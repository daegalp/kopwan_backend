# API Contract - Anggota
- [1) Get Anggota By No [GET]](#get)
- [2) Get All Anggota [GET]](#getAll)
- [3) Create Anggota [POST]](#create)
- [4) Update Anggota [PUT]](#update)
- [5) Delete Anggota [DELETE]](#delete)
- [6) Search Anggota By Name [GET]](#search)

<br>

# 1) Get Anggota - Anggota<a name="get"></a>
### GET /api/anggota/{no}

### Response Body

	{
		"requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
		"errorMessage": null,
		"errorCode": null,
		"success": true,
		"content": {
		    "no" : "",
		    "name" : "",
		    "rw" : ""
		}
	}

<br>

# 2) Get All Anggota Order By Rw<a name="getAll"></a>
### GET /api/anggota

### Params
- OPTIONAL - page (default = 1)
- OPTIONAL - size (default = 10)

### Response Body

	{
		"requestId": "",
		"errorMessage": null,
		"errorCode": null,
		"success": true,
		"content" : [
			{
				"no" : "",
				"name" : "",
				"rw" : ""
			}
		],
		"pageMetaData" : {	
			"pageNumber" : 1,
			"pageSize" : 2,
			"totalRecords" : 75
		}
	}

<br>

# 3) Create Anggota<a name="create"></a>
### POST /api/anggota

### Validation
- no anggota should unique

### Request Body

	{
		"no" : "",
		"name" : "",
		"rw" : 1 - 9 
	}

### Response Body

	{
		"requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
		"errorMessage": null,
		"errorCode": null,
		"success": true
	}

<br>

# 4) Update Anggota<a name="update"></a>
### PUT /api/anggota

### Validation
- no anggota should unique
- semua request body harus diisi

### Request Body

	{
		"no" : "",
		"name" : "",
		"rw" : 1 - 9 
	}

### Response Body

	{
        "requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
        "errorMessage": null,
        "errorCode": null,
        "success": true,
        "content": {
            "no" : "",
            "name" : "",
            "rw" : ""
        }
    }

<br>

# 5) Delete Anggota<a name="delete"></a>
### DELETE /api/anggota/{no}

### Validation
- no anggota should unique

### Response Body

	{
		"requestId": "3915644d-b025-4f8d-aba1-0e0e2bce4ba9",
		"errorMessage": null,
		"errorCode": null,
		"success": true
	}

<br>

# 6) Search Anggota By Name<a name="search"></a>
### GET /api/anggota/search

### Params
- OPTIONAL - name - like filter
- OPTIONAL - page (default = 1)
- OPTIONAL - size (default = 10)

### Response Body

	{
		"requestId": "",
		"errorMessage": null,
		"errorCode": null,
		"success": true,
		"content" : [
			{
				"no" : "",
				"name" : "",
				"rw" : ""
			}
		],
		"pageMetaData" : {	
			"pageNumber" : 1,
			"pageSize" : 2,
			"totalRecords" : 75
		}
	}

<br>