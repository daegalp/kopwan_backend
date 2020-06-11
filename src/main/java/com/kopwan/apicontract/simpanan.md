# API Contract - Simpanan
- [1) Get Simpanan by Id [GET]](#getById)
- [2) Create Simpanan [POST]](#create)

<br>

# 1) Get Simpanan<a name="getById"></a>
### GET /api/simpanan/{id}

### Response Body

	{
		"requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
		"errorMessage": null,
		"errorCode": null,
		"success": true,
		"content": {
		    "id": "5ee1b4f91fd9f218b417ca5a",
		    "anggota": {
		        "no": "2",
		        "name": "ega",
		        "rw": 3
		     },
		     "type": "POKOK",
		     "nominal": 30000,
		     "month": "Januari",
		     "year": 2020
		}
	}

<br>

# 2) Create Simpanan<a name="create"></a>
### POST /api/simpanan

### Request Body

	{
		"anggota": {
		    "no": "2",
		    "name": "ega",
		    "rw": 3
		 },
		 "type": "POKOK",
		 "nominal": 30000,
		 "month": "Januari",
		 "year": 2020
	}

### Response Body

	{
		"requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
		"errorMessage": null,
		"errorCode": null,
		"success": true
	}

<br>