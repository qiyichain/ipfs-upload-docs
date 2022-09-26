/**
 * author: yqq
 * date: 2022-09-26
 * description: java实现nft.storage上传和状态查询的demo
 */


// Import classes:
import org.threeten.bp.OffsetDateTime;
import storage.nft.ApiClient;
import storage.nft.ApiException;
import storage.nft.Configuration;
import storage.nft.auth.*;
//import storage.nft.models.*;
import storage.nft.api.NftStorageApi;
import storage.nft.model.DeleteResponse;
import storage.nft.model.GetResponse;
import storage.nft.model.ListResponse;
import storage.nft.model.UploadResponse;

import java.io.File;
import java.nio.file.FileSystemNotFoundException;
import java.util.Arrays;

public class Example {

    private static String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkaWQ6ZXRocjoweDYzOWU4QzljNkY4YkJhMjQ4NGFFRkE2QzI3MjY5NkY0OGZjZjk2MDIiLCJpc3MiOiJuZnQtc3RvcmFnZSIsImlhdCI6MTY2NDE1NTA3OTU4OCwibmFtZSI6InRlc3QifQ.LRDCI3XT_pMKzdl19kbjPcSY9mF27DTzuRZ3OCLvLmk";


    /**
     * upload 上传文件
     */
    public static void upload(NftStorageApi apiInstance) {
         String filePath = "/home/yqq/qiyichain/ipfs-upload-docs/go-demo/imgs/1.json";
//         String filePath = "/home/yqq/qiyichain/ipfs-upload-docs/go-demo/imgs/1.jpg";
//         String filePath = "/home/yqq/qiyichain/ipfs-upload-docs/go-demo/imgs/1.mp4";
//        String filePath = "/home/yqq/qiyichain/ipfs-upload-docs/go-demo/imgs/1.gif";
//        String filePath = "/home/yqq/qiyichain/ipfs-upload-docs/go-demo/imgs/1.png";
//        String filePath = "/home/yqq/qiyichain/ipfs-upload-docs/go-demo/imgs/1.mp3";

        // 异步
        // apiInstance.storeAsync()
        try {
            File uploadFile = new File(filePath);
            if(!uploadFile.exists()) {
                throw new FileSystemNotFoundException();
            }

            UploadResponse response = apiInstance.store(uploadFile);
            System.out.println("ok " + response);
        } catch (ApiException e) {
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }

    }


    /**
     * list 显示已经上传的(pinned)的文件
     */
    public static void list(NftStorageApi apiInstance) {

        OffsetDateTime.now();
        try {
            ListResponse rsp = apiInstance.list(OffsetDateTime.now(),100);
            System.out.println("rsp = " + rsp);
//            System.out.println("res = " + rsp.getOk());
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

    private  static  void getstatus( NftStorageApi apiInstance) {
        try {
            GetResponse resp = apiInstance.status("bafkreihqn6o4pzuq5q7c25rniveub6r3cbjwx25umscdqa7n2hjtdv7nny");
            System.out.println("response is " + resp);
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.nft.storage");

        // Configure HTTP bearer authorization: bearerAuth
        HttpBearerAuth bearerAuth = (HttpBearerAuth) defaultClient.getAuthentication("bearerAuth");
        bearerAuth.setBearerToken(Example.accessToken);

        NftStorageApi apiInstance = new NftStorageApi(defaultClient);

//        upload(apiInstance);

        list(apiInstance);

//        getstatus(apiInstance);


//        String cid = "cid_example"; // String | CID for the NFT
//        try {
//            DeleteResponse result = apiInstance.delete(cid);
//            System.out.println(result);
//        } catch (ApiException e) {
//            System.err.println("Exception when calling NftStorageApi#delete");
//            System.err.println("Status code: " + e.getCode());
//            System.err.println("Reason: " + e.getResponseBody());
//            System.err.println("Response headers: " + e.getResponseHeaders());
//            e.printStackTrace();
//        }
    }
}
