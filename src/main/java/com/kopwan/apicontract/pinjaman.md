# API Contract - Pinjaman
- [1) Create Pinjaman [POST]](#create)
- [2) Update Pinjaman [PUT]](#update)
- [3) Delete Pinjaman [DELETE]](#delete)
- [4) Filter Pinjaman [GET]](#filter)
- [5) Update Lunas Pinjaman [PUT]](#lunas)

# 1) Create Pinjaman<a name="create"></a>
### POST /api/pinjaman

### Validation
- anggota must be complete

### Request Body

	{
		"anggota" : {
		    "no" : "3",
		    "name" : "kuuhaku",
		    "rw" : 3
		 },
		 "nominal" : 30000,
		 "month" : 2,
		 "year" : 2020,
		 "target" : 10
	}

### Response Body

	{
		"requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
		"errorMessage": null,
		"errorCode": null,
		"success": true
	}

<br>

# 2) Update Pinjaman<a name="update"></a>
### PUT /api/pinjaman/{id}

### Validation
- id should unique
- semua request body harus diisi

### Request Body

	{
		"anggota" : {
		    "no" : "3",
		    "name" : "kuuhaku",
		    "rw" : 3
		 },
		 "lunas" : false,
		 "nominal" : 30000,
		 "month" : 2,
		 "year" : 2020,
		 "actual" : 0,
		 "target" : 10
	}

### Response Body

	{
        "requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
        "errorMessage": null,
        "errorCode": null,
        "success": true,
        "content": {
            "id" : uuid-123,
            "anggota" : {
                "no" : "3",
                "name" : "kuuhaku",
                "rw" : 3
             },
             "lunas" : false,
             "nominal" : 30000,
             "month" : 2,
             "year" : 2020,
             "actual" : 0,
             "target" : 10
        }
    }

<br>

# 3) Delete Pinjaman<a name="delete"></a>
### DELETE /api/pinjaman/{id}

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

# 4) Filter Pinjaman <a name="filter"></a>
### GET /api/pinjaman

### Params
- OPTIONAL - page
- OPTIONAL - size
- OPTIONAL - rw (1 - 9)
- OPTIONAL - no --> no anggota
- OPTIONAL - name --> using like filter
- OPTIONAL - month
- OPTIONAL - year

### Response Body
	
	{
		"requestId": "ff187083-5bca-4501-a072-9a536eccd2be",
            "errorMessage": null,
            "errorCode": null,
            "success": true,
            "content": [
                {
                    "id": "5ee2f2e06263da10e44ebea7",
                    "anggota": {
                        "no": "3",
                        "name": "kuuhaku",
                        "rw": 3
                    },
                    "nominal": 30000,
                    "month": 2,
                    "year": 2020,
                    "lunas": false,
                    "actual": 0,
                    "target": 10
                }
            ],
            "pageMetaData": {
                "pageSize": 10,
                "pageNumber": 1,
                "totalRecords": 2
            }
	}

<br>

# 5) Update Lunas Pinjaman<a name="lunas"></a>
### PUT /api/pinjaman

### Validation
- no anggota should unique
- semua request body harus diisi

### Request Body

	{
		 "no" : 3,
		 "month" : 3,
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