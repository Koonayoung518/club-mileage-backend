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
post /events
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

![image](https://user-images.githubusercontent.com/78741410/178099200-c35004f2-7911-49ba-a33c-2d63bc37f56e.png)

리뷰 수정 시


</div>
</details>


<details>
<summary>포인트 조회 API</summary>
<div markdown="1">    

``` json
get /events
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
get /point/total
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
post /review
{
  "file" : MultipartFile
	"requestDto" :{
                  "placeId": String,
                  "userId": String,
                  "content" : String
  }
}
```
![image](https://user-images.githubusercontent.com/78741410/178096289-d57f84f5-0e92-4731-864f-212555c5470a.png)

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
![image](https://user-images.githubusercontent.com/78741410/178097436-be8dc95a-e4cd-4dfb-b73a-3a40fc253b06.png)

</div>
</details>

<details>
<summary>리뷰 삭제 API</summary>
<div markdown="1">    

``` json
delete /review/{userId}/{reviewId}

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

