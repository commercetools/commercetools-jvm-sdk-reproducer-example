This is a minimal Maven project to show how to present problems in a reproducible way.

As a result you can fork it to create a test which shows your problem.

### this example

Run `mvn test` to run the test `DemoTest`. In the first run it will warn you about a missing configuration file:

```
Running DemoTest
Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 0.179 sec <<< FAILURE!
DemoTest  Time elapsed: 0.178 sec  <<< ERROR!
java.lang.RuntimeException: cannot read commercetools credentials file in /Users/username/dev/commercetools-jvm-sdk-reproducer-example/integrationtest.properties
with content like:
projectKey=YOUR project key
clientId=YOUR client id
clientSecret=YOUR client secret
apiUrl=https://api.sphere.io
authUrl=https://auth.sphere.io
```

So create the file integrationtest.properties and add the credentials of a new commercetools project (trial is good enough).

Run `mvn test` again and then it will show the exception. If you want to send sth. to the commercetools support, please don't just send the flat error message `detailMessage: Attribute definition for 'size' does not exist on type 'demo-product-type'.` but the whole stacktrace including the exception cause (in this example is no cause):


```
io.sphere.sdk.client.ErrorResponseException: detailMessage: Attribute definition for 'size' does not exist on type 'demo-product-type'.
summary: POST yet-another-shop-99/products failed  with response code 400 with X-Correlation-ID `nginx-93392013-f131-41b4-ba1f-49ef7b91168c`
http response formatted body: {
  "statusCode" : 400,
  "message" : "Attribute definition for 'size' does not exist on type 'demo-product-type'.",
  "errors" : [ {
    "code" : "InvalidOperation",
    "message" : "Attribute definition for 'size' does not exist on type 'demo-product-type'."
  } ]
}
http response: io.sphere.sdk.http.HttpResponseImpl@540f6158[statusCode=400,headers={Transfer-Encoding=[chunked], Server=[nginx], Access-Control-Allow-Origin=[*], Access-Control-Allow-Methods=[GET, POST, DELETE, OPTIONS], X-Correlation-ID=[nginx-93392013-f131-41b4-ba1f-49ef7b91168c], X-Served-By=[api-agitated-rosalind.sphere.prod.commercetools.de], Connection=[close], X-Served-Config=[sphere-projects-ws-1.0], Access-Control-Allow-Headers=[Accept, Authorization, Content-Type, Origin], Date=[Tue, 16 Feb 2016 13:27:05 GMT], Content-Type=[application/json; charset=utf-8]},associatedRequest=<null>,textInterpretedBody={"statusCode":400,"message":"Attribute definition for 'size' does not exist on type 'demo-product-type'.","errors":[{"code":"InvalidOperation","message":"Attribute definition for 'size' does not exist on type 'demo-product-type'."}]}]
SDK: 1.0.0-RC1
project: yet-another-shop-99
endpoint: POST /products
Java: 1.8.0_66
cwd: /Users/username/dev/commercetools-jvm-sdk-reproducer-example
date: Tue Feb 16 14:27:05 CET 2016
sphere request: ProductCreateCommandImpl[body=ProductDraftImpl[productType=io.sphere.sdk.models.ResourceIdentifierImpl@162d1a75,name=LocalizedString(en -> name),slug=LocalizedString(en -> name-80566299),description=<null>,categories=[],metaTitle=<null>,metaDescription=<null>,metaKeywords=<null>,masterVariant=ProductVariantDraftImpl[sku=<null>,prices=[],attributes=[AttributeDraftImpl[name=color,value={"en":"red","de":"rot"}], AttributeDraftImpl[name=size,value="M"]],images=[]],variants=[],taxCategory=<null>,searchKeywords=SearchKeywords[content={}],state=<null>,categoryOrderHints=<null>],expansionModel=ProductExpansionModelImpl[pathExpressions=[]],javaType=[simple type, class io.sphere.sdk.products.Product],endpoint=/products,expansionPaths=[],creationFunction=io.sphere.sdk.products.commands.ProductCreateCommandImpl$$Lambda$120/1281025083@afdf51a]
http request formatted body: {
  "productType" : {
    "key" : "demo-product-type"
  },
  "name" : {
    "en" : "name"
  },
  "slug" : {
    "en" : "name-80566299"
  },
  "categories" : [ ],
  "masterVariant" : {
    "prices" : [ ],
    "attributes" : [ {
      "name" : "color",
      "value" : {
        "en" : "red",
        "de" : "rot"
      }
    }, {
      "name" : "size",
      "value" : "M"
    } ],
    "images" : [ ]
  },
  "variants" : [ ],
  "searchKeywords" : { }
}
additional notes: []
Javadoc: http://sphereio.github.io/sphere-jvm-sdk/javadoc/1.0.0-RC1/io/sphere/sdk/client/ErrorResponseException.html

	at io.sphere.sdk.client.ExceptionFactory.lambda$of$32(ExceptionFactory.java:53)
	at io.sphere.sdk.client.ExceptionFactory.lambda$whenStatus$21(ExceptionFactory.java:31)
	at io.sphere.sdk.client.ExceptionFactory.createException(ExceptionFactory.java:72)
	at io.sphere.sdk.client.SphereClientImpl.createExceptionFor(SphereClientImpl.java:115)
	at io.sphere.sdk.client.SphereClientImpl.parse(SphereClientImpl.java:100)
	at io.sphere.sdk.client.SphereClientImpl.processHttpResponse(SphereClientImpl.java:94)
	at io.sphere.sdk.client.SphereClientImpl.lambda$execute$52(SphereClientImpl.java:68)
	at java.util.concurrent.CompletableFuture.uniApply(CompletableFuture.java:602)
	at java.util.concurrent.CompletableFuture$UniApply.tryFire(CompletableFuture.java:577)
	at java.util.concurrent.CompletableFuture.postComplete(CompletableFuture.java:474)
	at java.util.concurrent.CompletableFuture.postFire(CompletableFuture.java:561)
	at java.util.concurrent.CompletableFuture$UniApply.tryFire(CompletableFuture.java:580)
	at java.util.concurrent.CompletableFuture$Completion.exec(CompletableFuture.java:443)
	at java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:289)
	at java.util.concurrent.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1056)
	at java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1692)
	at java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:157)
```

This already gives us a lot of information:

```
summary: POST yet-another-shop-99/products failed  with response code 400 with X-Correlation-ID `nginx-93392013-f131-41b4-ba1f-49ef7b91168c`
```

The summary gives the http method, the commercetools project, the error code and the correlation id to look the request up in the server logs.

The server logs may not contain the payload of the request and response so they are also displayed as formatted JSON.

### use verbose logging

Sometimes it is a lot of effort to transport the problem to a minimal project with an exception
or not even an exception occurs but the still the behaviour of the platform is unexpected.

With an increased log level it is also possible to reproduce a problem.

In this example we use the [logback](http://logback.qos.ch/) to configure the logger.
Have a look at the [logback-test.xml configuration file](src/test/resources/logback-test.xml).

In this problem example we only use two endpoints: `product-types` and `products`. To log everything what happens there
we add to the logback-test.xml file after the entry `<logger name="sphere" level="INFO" />`:

```
<logger name="sphere.product-types" level="TRACE" />
<logger name="sphere.products" level="TRACE" />
```

Then running the tests we get the following log entries:

```
Running DemoTest
15:05:53.962 [pool-2-thread-1] DEBUG sphere.product-types.requests.queries - ProductTypeByKeyGetImpl[javaType=[simple type, class io.sphere.sdk.producttypes.ProductType],endpoint=/product-types,identifierToSearchFor=key=demo-product-type,expansionPaths=[],additionalParameters=[],expansionModel=ProductTypeExpansionModelImpl[pathExpressions=[]],builderFunction=io.sphere.sdk.producttypes.queries.ProductTypeByKeyGetImpl$$Lambda$59/2109839984@36d753e]
15:05:53.964 [pool-2-thread-1] TRACE sphere.product-types.requests.queries - no request body present
15:05:54.320 [ForkJoinPool-1-worker-1] DEBUG sphere.product-types.responses.queries - io.sphere.sdk.http.HttpResponseImpl@60e76949[statusCode=404,headers={Transfer-Encoding=[chunked], Server=[nginx], Access-Control-Allow-Origin=[*], Access-Control-Allow-Methods=[GET, POST, DELETE, OPTIONS], X-Served-By=[api02.sphere.prod.commercetools.de], X-Correlation-ID=[nginx-b534a498-8353-4c71-a31c-943fb1b0b140], Connection=[keep-alive], X-Served-Config=[sphere-projects-ws-1.0], Access-Control-Allow-Headers=[Accept, Authorization, Content-Type, Origin], Date=[Tue, 16 Feb 2016 14:05:54 GMT], Content-Type=[application/json; charset=utf-8]},associatedRequest=HttpRequestImpl[httpMethod=GET,url=https://api.sphere.io/yet-another-shop-99/product-types/key=demo-product-type,headers={Authorization=[**removed from output**], User-Agent=[SPHERE.IO JVM SDK 1.0.0-RC1], Accept-Encoding=[gzip]},body=<null>],textInterpretedBody={"statusCode":404,"message":"The Resource with key 'demo-product-type' was not found.","errors":[{"code":"InvalidSubject","message":"The Resource with key 'demo-product-type' was not found."}]}]
15:05:54.340 [ForkJoinPool-1-worker-1] TRACE sphere.product-types.responses.queries - 404
{
  "statusCode" : 404,
  "message" : "The Resource with key 'demo-product-type' was not found.",
  "errors" : [ {
    "code" : "InvalidSubject",
    "message" : "The Resource with key 'demo-product-type' was not found."
  } ]
}

15:05:54.392 [main] DEBUG sphere.product-types.requests.commands - ProductTypeCreateCommandImpl[body=ProductTypeDraftImpl[key=demo-product-type,name=demo-product-type,description=demo-product-type,attributes=[AttributeDefinitionImpl[attributeType=LocalizedStringAttributeType[],name=color,label=LocalizedString(de -> Farbe, en -> color),isRequired=false,attributeConstraint=NONE,isSearchable=true,inputHint=SINGLE_LINE]]],expansionModel=ProductTypeExpansionModelImpl[pathExpressions=[]],javaType=[simple type, class io.sphere.sdk.producttypes.ProductType],endpoint=/product-types,expansionPaths=[],creationFunction=io.sphere.sdk.producttypes.commands.ProductTypeCreateCommandImpl$$Lambda$100/1323434987@4ea5b703]
15:05:54.394 [main] TRACE sphere.product-types.requests.commands - send: {"key":"demo-product-type","name":"demo-product-type","description":"demo-product-type","attributes":[{"type":{"name":"ltext"},"name":"color","label":{"en":"color","de":"Farbe"},"isRequired":false,"attributeConstraint":"None","isSearchable":true,"inputHint":"SingleLine"}]}
formatted: {
  "key" : "demo-product-type",
  "name" : "demo-product-type",
  "description" : "demo-product-type",
  "attributes" : [ {
    "type" : {
      "name" : "ltext"
    },
    "name" : "color",
    "label" : {
      "en" : "color",
      "de" : "Farbe"
    },
    "isRequired" : false,
    "attributeConstraint" : "None",
    "isSearchable" : true,
    "inputHint" : "SingleLine"
  } ]
}
15:05:54.483 [ForkJoinPool-1-worker-1] DEBUG sphere.product-types.responses.commands - io.sphere.sdk.http.HttpResponseImpl@67832b0c[statusCode=201,headers={Transfer-Encoding=[chunked], Server=[nginx], Access-Control-Allow-Origin=[*], Access-Control-Allow-Methods=[GET, POST, DELETE, OPTIONS], X-Served-By=[api02.sphere.prod.commercetools.de], X-Correlation-ID=[nginx-f1f4c7e4-7087-4a31-846e-62db1ac2b224], Connection=[keep-alive], X-Served-Config=[sphere-projects-ws-1.0], Access-Control-Allow-Headers=[Accept, Authorization, Content-Type, Origin], Date=[Tue, 16 Feb 2016 14:05:54 GMT], Content-Type=[application/json; charset=utf-8]},associatedRequest=HttpRequestImpl[httpMethod=POST,url=https://api.sphere.io/yet-another-shop-99/product-types,headers={Authorization=[**removed from output**], User-Agent=[SPHERE.IO JVM SDK 1.0.0-RC1], Accept-Encoding=[gzip]},body=io.sphere.sdk.http.StringHttpRequestBody@3cea61c4[body={"key":"demo-product-type","name":"demo-product-type","description":"demo-product-type","attributes":[{"type":{"name":"ltext"},"name":"color","label":{"en":"color","de":"Farbe"},"isRequired":false,"attributeConstraint":"None","isSearchable":true,"inputHint":"SingleLine"}]}]],textInterpretedBody={"id":"dfbeea59-5aa1-4a76-a73a-1383296c1a56","version":1,"name":"demo-product-type","description":"demo-product-type","classifier":"Complex","attributes":[{"name":"color","label":{"en":"color","de":"Farbe"},"isRequired":false,"type":{"name":"ltext"},"attributeConstraint":"None","isSearchable":true,"inputHint":"SingleLine","displayGroup":"Other"}],"key":"demo-product-type","createdAt":"2016-02-16T14:05:54.464Z","lastModifiedAt":"2016-02-16T14:05:54.464Z"}]
15:05:54.484 [ForkJoinPool-1-worker-1] TRACE sphere.product-types.responses.commands - 201
{
  "id" : "dfbeea59-5aa1-4a76-a73a-1383296c1a56",
  "version" : 1,
  "name" : "demo-product-type",
  "description" : "demo-product-type",
  "classifier" : "Complex",
  "attributes" : [ {
    "name" : "color",
    "label" : {
      "en" : "color",
      "de" : "Farbe"
    },
    "isRequired" : false,
    "type" : {
      "name" : "ltext"
    },
    "attributeConstraint" : "None",
    "isSearchable" : true,
    "inputHint" : "SingleLine",
    "displayGroup" : "Other"
  } ],
  "key" : "demo-product-type",
  "createdAt" : "2016-02-16T14:05:54.464Z",
  "lastModifiedAt" : "2016-02-16T14:05:54.464Z"
}

15:05:54.534 [main] DEBUG sphere.products.requests.commands - ProductCreateCommandImpl[body=ProductDraftImpl[productType=io.sphere.sdk.models.ResourceIdentifierImpl@398dada8,name=LocalizedString(en -> name),slug=LocalizedString(en -> name-73139769),description=<null>,categories=[],metaTitle=<null>,metaDescription=<null>,metaKeywords=<null>,masterVariant=ProductVariantDraftImpl[sku=<null>,prices=[],attributes=[AttributeDraftImpl[name=color,value={"en":"red","de":"rot"}], AttributeDraftImpl[name=size,value="M"]],images=[]],variants=[],taxCategory=<null>,searchKeywords=SearchKeywords[content={}],state=<null>,categoryOrderHints=<null>],expansionModel=ProductExpansionModelImpl[pathExpressions=[]],javaType=[simple type, class io.sphere.sdk.products.Product],endpoint=/products,expansionPaths=[],creationFunction=io.sphere.sdk.products.commands.ProductCreateCommandImpl$$Lambda$130/1513608173@46271dd6]
15:05:54.535 [main] TRACE sphere.products.requests.commands - send: {"productType":{"key":"demo-product-type"},"name":{"en":"name"},"slug":{"en":"name-73139769"},"categories":[],"masterVariant":{"prices":[],"attributes":[{"name":"color","value":{"en":"red","de":"rot"}},{"name":"size","value":"M"}],"images":[]},"variants":[],"searchKeywords":{}}
formatted: {
  "productType" : {
    "key" : "demo-product-type"
  },
  "name" : {
    "en" : "name"
  },
  "slug" : {
    "en" : "name-73139769"
  },
  "categories" : [ ],
  "masterVariant" : {
    "prices" : [ ],
    "attributes" : [ {
      "name" : "color",
      "value" : {
        "en" : "red",
        "de" : "rot"
      }
    }, {
      "name" : "size",
      "value" : "M"
    } ],
    "images" : [ ]
  },
  "variants" : [ ],
  "searchKeywords" : { }
}
15:05:54.618 [ForkJoinPool-1-worker-1] DEBUG sphere.products.responses.commands - io.sphere.sdk.http.HttpResponseImpl@6ff0baf[statusCode=400,headers={Transfer-Encoding=[chunked], Server=[nginx], Access-Control-Allow-Origin=[*], Access-Control-Allow-Methods=[GET, POST, DELETE, OPTIONS], X-Correlation-ID=[nginx-ad578ff8-ae01-4c8f-8dba-80ee5727ba79], X-Served-By=[api-agitated-rosalind.sphere.prod.commercetools.de], Connection=[close], X-Served-Config=[sphere-projects-ws-1.0], Access-Control-Allow-Headers=[Accept, Authorization, Content-Type, Origin], Date=[Tue, 16 Feb 2016 14:05:54 GMT], Content-Type=[application/json; charset=utf-8]},associatedRequest=HttpRequestImpl[httpMethod=POST,url=https://api.sphere.io/yet-another-shop-99/products,headers={Authorization=[**removed from output**], User-Agent=[SPHERE.IO JVM SDK 1.0.0-RC1], Accept-Encoding=[gzip]},body=io.sphere.sdk.http.StringHttpRequestBody@4d84be31[body={"productType":{"key":"demo-product-type"},"name":{"en":"name"},"slug":{"en":"name-73139769"},"categories":[],"masterVariant":{"prices":[],"attributes":[{"name":"color","value":{"en":"red","de":"rot"}},{"name":"size","value":"M"}],"images":[]},"variants":[],"searchKeywords":{}}]],textInterpretedBody={"statusCode":400,"message":"Attribute definition for 'size' does not exist on type 'demo-product-type'.","errors":[{"code":"InvalidOperation","message":"Attribute definition for 'size' does not exist on type 'demo-product-type'."}]}]
15:05:54.618 [ForkJoinPool-1-worker-1] TRACE sphere.products.responses.commands - 400
{
  "statusCode" : 400,
  "message" : "Attribute definition for 'size' does not exist on type 'demo-product-type'.",
  "errors" : [ {
    "code" : "InvalidOperation",
    "message" : "Attribute definition for 'size' does not exist on type 'demo-product-type'."
  } ]
}

```
