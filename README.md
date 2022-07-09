# club-mileage-backend

----------------
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
![image](https://user-images.githubusercontent.com/78741410/178092516-7ed5a626-7e77-4106-af31-9b008f6e934a.png)

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
