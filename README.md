# club-mileage-backend

----------------
## ec2 서버 주소

http://ec2-43-200-115-220.ap-northeast-2.compute.amazonaws.com:8080

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

``` json
{
    "id": "e8f676ba-439f-49d8-bc66-aeced83a1301",
    "dateTime": "2022-07-09T10:38:48.861+00:00",
    "status": 200,
    "message": "포인트 적립 성공",
    "list": {
        "action": "DELETE",
        "point": -3
    }
}
```

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
    "id": "0a9d9ae6-4956-4135-bc3b-94f20f909a91",
    "dateTime": "2022-07-09T10:39:47.329+00:00",
    "status": 200,
    "message": "포인트 조회 성공",
    "list": {
        "pointTotal": 0,
        "historyList": [
            {
                "createdAt": "2022-07-09T10:29:12.320+00:00",
                "targetId": "30e908a7-0f1a-438c-8723-8f03fe7356e0",
                "eventType": "REVIEW",
                "actionType": "ADD",
                "point": 2
            },
            {
                "createdAt": "2022-07-09T10:32:04.681+00:00",
                "targetId": "30e908a7-0f1a-438c-8723-8f03fe7356e0",
                "eventType": "REVIEW",
                "actionType": "MOD",
                "point": 1
            },
            {
                "createdAt": "2022-07-09T10:38:48.850+00:00",
                "targetId": "30e908a7-0f1a-438c-8723-8f03fe7356e0",
                "eventType": "REVIEW",
                "actionType": "DELETE",
                "point": -3
            }
        ]
    }
}
```

</div>
</details>

<details>
<summary>포인트 총합 조회 API</summary>
<div markdown="1">    

``` json
GET /point/total
{
   "userId": String
}
```
``` json
{
    "id": "699eb9a6-dad8-40f3-a5e9-6f223cb35100",
    "dateTime": "2022-07-09T10:37:45.364+00:00",
    "status": 200,
    "message": "포인트 총합 조회 성공",
    "list": {
        "pointTotal": 3
    }
}
```

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
            "35603c13-2ee6-467e-9d41-3e41e6675aab"
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

``` json
{
    "id": "f99efac9-c5cf-4323-9fb7-f18d81c2d800",
    "dateTime": "2022-07-09T10:35:27.616+00:00",
    "status": 200,
    "message": "리뷰 삭제 성공",
    "list": {
        "type": "REVIEW",
        "action": "DELETE",
        "reviewId": "30e908a7-0f1a-438c-8723-8f03fe7356e0",
        "content": "리뷰내용",
        "attachedPhotoIds": [
            "35603c13-2ee6-467e-9d41-3e41e6675aab"
        ],
        "placeId": "4c8f1f79-f28d-45ff-8f8f-ad0bb38520c3",
        "userId": "f123e027-bdc3-4bf2-8ec3-f781cf8d2628"
    }
}
```
	
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
	
``` json
{
    "id": "f3391279-15e4-4a76-ac4f-d09f42d3f2f8",
    "dateTime": "2022-07-09T10:33:45.289+00:00",
    "status": 200,
    "message": "내가 작성한 리뷰 조회 성공",
    "list": [
        {
            "reviewId": "30e908a7-0f1a-438c-8723-8f03fe7356e0",
            "content": "리뷰내용"
        }
    ]
}
```

</div>
</details>

