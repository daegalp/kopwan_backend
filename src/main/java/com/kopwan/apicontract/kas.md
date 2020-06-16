# API Contract - Kas
- [1) Get Kas By Id [GET]](#get)
- [2) Create Kas [POST]](#create)
- [3) Update Kas [PUT]](#update)
- [4) Delete Kas [PUT]](#delete)
- [5) Generate Buku Kas [POST]](#generate)
- [6) Get Kas By Month And Year [GET]](#getAll)

<br>

# 1) Get Kas - Kas<a name="get"></a>
### GET /api/kas/{id}

### VALIDATION
- alurKas - MASUK / KELUAR

### Response Body

	{
		"requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
		"errorMessage": null,
		"errorCode": null,
		"success": true,
		"content": {
		    "id": "5ee7083d2159f825677ba305",
		    "name": "rw 1",
		    "alurKas": "masuk",
		    "nominal": 50000,
		    "month": 2,
		    "year": 2020
		}
	}

<br>

# 2) Create kas<a name="create"></a>
### POST /api/kas

### Validation
- alurKas - MASUK / KELUAR

### Request Body

	{
		"name" : "rw 1",
		"type" : "",
		"alurKas" : "masuk",
		"nominal" : 50000,
		"month" : 2,
		"year" : 2020
	}

### Response Body

	{
		"requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
		"errorMessage": null,
		"errorCode": null,
		"success": true
	}

<br>

# 3) Update Kas<a name="update"></a>
### PUT /api/kas/{id}

### Validation
- semua request body harus diisi

### Request Body

	{
		"name" : "rw 1",
		"type" : "",
		"alurKas" : "keluar",
		"nominal" : 50000,
		"month" : 2,
		"year" : 2020
	}

### Response Body

	{
        "requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
        "errorMessage": null,
        "errorCode": null,
        "success": true,
        "content": {
            "id": "5ee7083d2159f825677ba305",
            "type" : "",
            "name": "rw 1",
            "alurKas": "keluar",
            "nominal": 50000,
            "month": 2,
            "year": 2020
        }
    }

<br>

# 4) Delete Kas<a name="delete"></a>
### DELETE /api/kas/{no}

### Validation
- id should unique

### Response Body

	{
		"requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
		"errorMessage": null,
		"errorCode": null,
		"success": true
	}

<br>

# 5) Generate Buku kas<a name="generate"></a>
### POST /api/kas/generate

### Request Body

	{
		"month" : 2,
		"year" : 2020
	}

### Response Body

	{
		"requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
		"errorMessage": null,
		"errorCode": null,
		"success": true
	}

<br>

# 6) Get Kas By Month And Year - Kas<a name="getAll"></a>
### GET /api/kas

### Params
- OPTIONAL - page
- OPTIONAL - size
- MUST - month
- MUST - year

### Response Body

	{
		"requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
		"errorMessage": null,
		"errorCode": null,
		"success": true,
		"content": [
                {
                    "id": "5ee7083d2159f825677ba305",
                    "name": "rw 1",
                    "alurKas": "KELUAR",
                    "nominal": 50000,
                    "month": 2,
                    "year": 2020
                }
		],
		"pageMetaData": {
                "pageSize": 10,
                "pageNumber": 1,
                "totalRecords": 7
            }
	}

<br>