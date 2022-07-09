# club-mileage-backend

----------------
## ec2 서버 주소

## 기술 스택
- Spring boot
- Java
- Mysql 8.0
- AWS (EC2, RDS, S3)
- flyway

## ERD  
![image](https://user-images.githubusercontent.com/78741410/178092157-a89628a4-88fc-41cd-8980-5930c7bbc93f.png)

## API
<details>
<summary>포인트 적립 API</summary>
<div markdown="1">    

``` json
POST /events
{
        "type": String,
        "action": String,
        "reviewId": String,
        "content": String,
        "attachedPhotoIds": [String],
        "placeId": String,
        "userId": String
}
```
리뷰 등록 시

``` json
{
    "id": "a8593965-4bac-4004-a65a-6d11bb58c33c",
    "dateTime": "2022-07-09T10:29:12.482+00:00",
    "status": 200,
    "message": "포인트 적립 성공",
    "list": {
        "action": "ADD",
        "point": 2
    }
}
```


리뷰 수정 시
	
``` json
{
    "id": "ce61f2bf-0419-4a8e-877a-6abfafef39de",
    "dateTime": "2022-07-09T10:32:04.709+00:00",
    "status": 200,
    "message": "포인트 적립 성공",
    "list": {
        "action": "MOD",
        "point": 1
    }
}
```
	
리뷰 삭제 시

![image](https://user-images.githubusercontent.com/78741410/178100962-31346e5c-2767-4db6-b891-064c9956bf57.png)



</div>
</details>


<details>
<summary>포인트 조회 API</summary>
<div markdown="1">    

``` json
GET /events
{
        "userId": String
}
```

``` json
{
    "id": "ac5f36b5-58e9-4f12-9f74-062830432cc1",
    "dateTime": "2022-07-09T09:36:17.605+00:00",
    "status": 200,
    "message": "포인트 조회 성공",
    "list": {
        "pointTotal": 2,
        "historyList": [
            {
                "createdAt": "2022-07-09T09:29:59.631+00:00",
                "targetId": "66162bcb-f4ca-4588-b7d6-c8f1412da1e4",
                "eventType": "REVIEW",
                "actionType": "ADD",
                "point": 2
            },
            {
                "createdAt": "2022-07-09T09:31:02.946+00:00",
                "targetId": "66162bcb-f4ca-4588-b7d6-c8f1412da1e4",
                "eventType": "REVIEW",
                "actionType": "MOD",
                "point": 1
            },
            {
                "createdAt": "2022-07-09T09:34:19.378+00:00",
                "targetId": "66162bcb-f4ca-4588-b7d6-c8f1412da1e4",
                "eventType": "REVIEW",
                "actionType": "MOD",
                "point": -1
            }
        ]
    }
}
```

</div>
</details>

<details>
<summary>포인트 총합 API</summary>
<div markdown="1">    

``` json
GET /point/total
{
   "userId": String
}
```
![image](https://user-images.githubusercontent.com/78741410/178099448-52496fa3-8bad-47c0-8d63-a6d998679370.png)


</div>
</details>

<details>
<summary>리뷰 작성 API</summary>
<div markdown="1">    

``` json
POST /review
{
  "file" : MultipartFile
	"requestDto" :{
                  "placeId": String,
                  "userId": String,
                  "content" : String
  }
}
```

``` json
{
    "id": "14c43c04-d050-4bd2-86f5-f120468038db",
    "dateTime": "2022-07-09T10:27:54.677+00:00",
    "status": 200,
    "message": "리뷰 등록 성공",
    "list": {
        "type": "REVIEW",
        "action": "ADD",
        "reviewId": "30e908a7-0f1a-438c-8723-8f03fe7356e0",
        "content": "리뷰내용",
        "attachedPhotoIds": [],
        "placeId": "4c8f1f79-f28d-45ff-8f8f-ad0bb38520c3",
        "userId": "f123e027-bdc3-4bf2-8ec3-f781cf8d2628"
    }
}
```

</div>
</details>

<details>
<summary>리뷰 수정 API</summary>
<div markdown="1">    

``` json
post /review/update
{
  "file" : MultipartFile
	"requestDto" :{
                  "placeId": String,
                  "userId": String,
                  "content" : String
  }
}
```

``` json
	
{
    "id": "94f75ce4-c844-4c5e-b241-337cca983cf5",
    "dateTime": "2022-07-09T10:31:03.642+00:00",
    "status": 200,
    "message": "리뷰 수정 성공",
    "list": {
        "type": "REVIEW",
        "action": "MOD",
        "reviewId": "30e908a7-0f1a-438c-8723-8f03fe7356e0",
        "content": "리뷰내용",
        "attachedPhotoIds": [
            "https://triplebucket.s3.ap-northeast-2.amazonaws.com/review/1da0a34f-8396-44c2-8384-1cdc10adf315test.png"
        ],
        "placeId": "4c8f1f79-f28d-45ff-8f8f-ad0bb38520c3",
        "userId": "f123e027-bdc3-4bf2-8ec3-f781cf8d2628"
    }
}
```

</div>
</details>

<details>
<summary>리뷰 삭제 API</summary>
<div markdown="1">    

``` json
DELETE /review/{userId}/{reviewId}

```
![image](https://user-images.githubusercontent.com/78741410/178097478-18cfcdfe-cd60-4f08-ae71-9daef2c642b6.png)
</div>
</details>

<details>
<summary>내가 작성한 리뷰 조회 API</summary>
<div markdown="1">    

``` json
GET /review
{
 "userId": String
}
```
![image](https://user-images.githubusercontent.com/78741410/178092576-23549427-3e3d-486e-b113-d52f0dfb2dee.png)

</div>
</details>

