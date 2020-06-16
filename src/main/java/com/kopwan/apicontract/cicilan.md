# API Contract - Cicilan Pinjaman
- [1) Filter Cicilan Pinjaman [GET]](#filter)
- [2) Delete Cicilan Pinjaman [DELETE]](#delete)
- [3) Create Cicilan Pinjaman [POST]](#create)
- [4) Update Cicilan Pinjaman [PUT]](#update)

# 1) Filter Cicilan Pinjaman <a name="filter"></a>
### GET /api/cicilan

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
                    "pokok" : 30000,
                    "jasa" : 5000,
                    "month": 2,
                    "year": 2020
                }
            ],
            "pageMetaData": {
                "pageSize": 10,
                "pageNumber": 1,
                "totalRecords": 2
            }
	}

<br>

# 2) Delete Cicilan Pinjaman<a name="delete"></a>
### DELETE /api/cicilan/{id}

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

# 3) Create Cicilan Pinjaman<a name="create"></a>
### POST /api/cicilan

### Validation
- no anggota should be unique

### Request Body

	{
		"no" : 3,
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

# 4) Update Cicilan Pinjaman<a name="update"></a>
### PUT /api/cicilan/{id}

### Validation
- id should unique
- semua request body harus diisi
- no anggota should unique

### Request Body

	{
	     "no" : 2,
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
            "id": "5ee5a5b66d41ad2546317677",
            "anggota": {
                "no": "3",
                "name": "kuuhaku",
                "rw": 3
            },
            "pokok": 3000,
            "jasa": 1500,
            "month": 2,
            "year": 2020
        }
    }

<br>