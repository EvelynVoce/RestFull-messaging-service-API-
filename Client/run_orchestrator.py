from requests import get


def get_id():
    url: str = "http://localhost:8080/api/orchestrator?service=ID"
    response = get(url, stream=True)
    print(json_response := response.json()["id"])
    return json_response
