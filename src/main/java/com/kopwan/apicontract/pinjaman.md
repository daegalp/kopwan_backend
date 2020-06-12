# API Contract - Pinjaman
- [1) Create Pinjaman [POST]](#create)

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