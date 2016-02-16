import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.ProductDraft;
import io.sphere.sdk.products.ProductDraftBuilder;
import io.sphere.sdk.products.ProductVariantDraft;
import io.sphere.sdk.products.ProductVariantDraftBuilder;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeByKeyGet;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;

public class DemoTest {
    @ClassRule
    public static SphereClientRule sphereClient = new SphereClientRule();

    private static final String productTypeKey = "demo-product-type";

    @BeforeClass
    public static void createData() {
        final ProductType productType = sphereClient.executeBlocking(ProductTypeByKeyGet.of(productTypeKey));
        if (productType == null) {
            createProductType();
        }
    }

    //here is a problem cause hidden for demo purposes
    private static ProductType createProductType() {
        final AttributeDefinition color = AttributeDefinitionBuilder
                .of("color", LocalizedString.of(ENGLISH, "color", GERMAN, "Farbe"), LocalizedStringAttributeType.of())
                .build();
        final AttributeDefinition size = AttributeDefinitionBuilder
                .of("size", LocalizedString.of(ENGLISH, "size", GERMAN, "Größe"),
                        EnumAttributeType.of(EnumValue.of("M", "M"), EnumValue.of("X", "X"), EnumValue.of("XL", "XL")))
                .build();
        final List<AttributeDefinition> attributeDefinitions = asList(color);
        final ProductTypeDraft of = ProductTypeDraft.of(productTypeKey, productTypeKey, productTypeKey, attributeDefinitions);
        return sphereClient.executeBlocking(ProductTypeCreateCommand.of(of));
    }

    @Test
    public void testShowingTheUnexpectedBehaviour() {
        final ResourceIdentifier<ProductType> productType = ResourceIdentifier.ofKey(productTypeKey);
        final LocalizedString name = LocalizedString.of(ENGLISH, "name");
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .plusAttribute(AttributeDraft.of("color", LocalizedString.of(ENGLISH, "red", GERMAN, "rot")))
                .plusAttribute(AttributeDraft.of("size", "M"))
                .build();
        final ProductDraft productDraft = ProductDraftBuilder.of(productType, name, name.slugifiedUnique(), masterVariant)
                .build();
        sphereClient.executeBlocking(ProductCreateCommand.of(productDraft));
    }
}
