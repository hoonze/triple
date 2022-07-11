## 어플리케이션 실행 방법

### 1. git clone

``` bash
$ git clone https://github.com/hoonze/triple.git
```

### 2. triple/backend 폴더 접근

```bash
$ cd triple/backend
```

### 3. 프로젝트 빌드

``` bash
$ ./gradlew bootjar
```

### 4. docker 컨테이너 실행

```bash
$ docker-compose up
```



## API

1. 리뷰 작성 이벤트

   ```
   url 
   /events
   
   input
   {
       "type":String
       "action":String
       "reviewId":String
       "content":String
       "attachedPhotoIds":String[]
       "userId":String
   }
   
   output
   {
       "success": boolean
       "response": {
           "reviewId": String
           "content": String
           "attachedPhotoIds": String[]
           "userId": String
           "placeId": String
       },
       "error": boolean
   }
   ```

   

2. 포인트 조회

   ```
   url 
   /points/{userId}
   
   output
   {
       "success": boolean
       "response": {
           "userId": String
           "point": int
       },
       "error": boolean
   }
   ```

   

## Software  Version

- JAVA : 1.8.0_192
- Spring boot : 2.7.1
- MySQL : 8.0.26
- Docker : 20.10.17
- Docker-compose : 1.29.2