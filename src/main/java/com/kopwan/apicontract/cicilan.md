# API Contract - Cicilan Pinjaman
- [1) Filter Pinjaman [GET]](#filter)

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
                    "month": "Februari",
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