# **Infor M3 Socket Adaptor** 
- an adaptor to provide easier M3 Socket Class MvxSockJ library.
- internally using 

## Update M3 Dependency
- m3 is a license software, we will not upload the jar here
- **update MvxSockJ dependency with your repo**

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
Map<String, String> resultSet = m3Api.callMethod(request);
```
