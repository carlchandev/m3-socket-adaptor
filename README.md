# **Infor M3 Socket Adaptor** 
- an adaptor to provide easier M3 Socket Class MvxSockJ library.
- no longer have to create clumsy socket with long list arguments
- MvxSockJ is wrapped with an adaptor and directly read the config from properties file
- M3Api can be mocked and unit tested because no more new instance required
- internally using MvxSockJ library

## Update M3 Dependency
- m3 is a license software, we will not upload the jar here
- **update MvxSockJ dependency with your repo in build.gradle**
```
// this dependency is not in any public repo. Please replace this with your own MvxSockJ dependency
compile "MvxAPI:MvxSockJ:1.1"
```

## Step 1 - Config Properties File
 Config **src/main/resources/m3-socket-api.properties** your m3 server host, port, username, password

## Step 2 - Construct Request
```
M3ApiRequest request = M3ApiRequest.builder()
        .programId("MMS200MI")
        .method("GetItmBasic")
        .requestParameter("CONO", "100")
        .requestParameter("ITNO", "THR0000037-0053")
        .responseFieldId("ITDS")
        .build();
```
## Step 3 - Call M3 API and Get Response as a Map
```
M3Api m3Api = new M3Api();
Map<String, String> resultSet = m3Api.callMethod(request);
```
