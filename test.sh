curl -X POST http://localhost:3000/reset -v
curl http://localhost:3000/app_status -v
curl -H "Content-Type: application/json" -X POST --data "{\"id\":\"i1\", \"title\":\"A\"}" http://localhost:3000/issue -v
curl -H "Content-Type: application/json" -X POST --data "{\"id\":\"i2\", \"title\":\"B\"}" http://localhost:3000/issue -v
curl -H "Content-Type: application/json" -X POST --data "{\"id\":\"i3\", \"title\":\"C\"}" http://localhost:3000/issue -v
curl http://localhost:3000/issue -v

curl -H "Content-Type: application/json" -X POST --data "{\"title\":\"New title\", \"version\":1 }" http://localhost:3000/issue/i1/amend/title -v
curl http://localhost:3000/app_status -v
curl -H "Content-Type: application/json" -X POST --data "{\"title\":\"New title B\", \"version\":1 }" http://localhost:3000/issue/i2/amend/title -v
curl http://localhost:3000/app_status -v
curl -H "Content-Type: application/json" -X POST --data "{\"title\":\"Update A Again\", \"version\":2 }" http://localhost:3000/issue/i1/amend/title -v
curl http://localhost:3000/app_status -v

