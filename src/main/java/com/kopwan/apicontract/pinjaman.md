# API Contract - Pinjaman
- [1) Create Pinjaman [POST]](#create)
- [2) Update Pinjaman [PUT]](#update)
- [3) Delete Pinjaman [DELETE]](#delete)

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
		 "month" : "Februari",
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
		 "month" : "Februari",
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
             "month" : "Februari",
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