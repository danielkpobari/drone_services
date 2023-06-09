## Drones Application API
---
:scroll: **START**
## Introduction
There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.
Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.
---
### Task description
We have a fleet of **10 drones**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.
A **Drone** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).
Each **Medication** has:
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).
Develop a service via REST API that allows clients to communicate with the drones (i.e. **dispatch controller**). The specific communicaiton with the drone is outside the scope of this task.
The service should allow:
- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone;
- checking available drones for loading;
- check drone battery level for a given drone;
> Feel free to make assumptions for the design approach.
---
### Requirements
While implementing your solution **please take care of the following requirements**:
#### Functional requirements
- There is no need for UI;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones battery levels and create history/audit event log for this.
---

#### Non-functional requirements
- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Required data must be preloaded in the database.
- JUnit tests are optional but advisable (if you have time);
- Advice: Show us how you work through your commit history.
---
#### How to build
#### Requirements
- Java 8;
- Java IDE : IntelliJ (or Eclipse, Vscode, Netbeans);
- Database: H2 Memory(All data will be preloaded on run);
- Postman(For testing );
Steps by step for building and runing the project locally;
Clone the from the link git clone https://github.com/danielkpobari/drone_services
- Open the cloned project in intelliJ Idea;
- Go to POM.xml the update Project to update all the maven dependencies;
- Maven Build the project and run;
---
#### Testing The API Endpoints
#### Some of the Assumptions made in the course of the task
- A drone can accommadate more than one medication at a time provided the total weight af all the medications does not exceed the max weight of the drone;
- preloaded IDs of Drones (**DJI_MAVIC03_AIR_2** , **DJI_MAVIC05_AIR_3** , **DJI_MAVIC07_AIR_4** **DJI_MAVIC01_AIR_1** , **DJI_MAVIC02_AIR_5** , **ASX_MAVIC05_AIR_7** , **0DJ_MAVIC34_AIR_2** , **DJS_MAVIC52_AIR_8** , **DSF_MAVIC08_AIR_2** , **AMP_MAVIC29_AIR_2**);
- preloaded IDs of Medications (**PC003**, **VT0D3** , **IB35OB**);
---
**Registering A Drone**
- Endpoint: 'http://127.0.0.1:8080/api/register-drone'; 
```
curl --location --request POST 'http://127.0.0.1:8080/api/register-drone' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
                "message": "Drone registered successfully",
                "data": {
                 "serialNumber":"DJI_MAVIC03_AIR_2",
                "model":"LIGHTWEIGHT",
                "batteryCapacity":"100",
                "state":"IDLE",
                "weight":120.0
            }
'
````
**Response**
```
{
    ""serialNumber": "DJI_MAVIC03_AIR_2",
        "model": "LIGHTWEIGHT",
        "state": "IDLE",
        "batteryCapacity": 100,
        "weight": 120.0,
        "medicationDto": null

}
```
---
**Loading A Drone**
- Endpoint: '(http://localhost:8080/api/load-drone/DJI_MAVIC03_AIR_2/PC003)';
- Path Variables:
   - DJI_MAVIC03_AIR_2 : Drone serialNumber;
   - PC003: Medication code;
**Payload**
```
curl --location --request GET '(http://localhost:8080/api/load-drone/DJI_MAVIC03_AIR_2/PC003)' \
--data-raw ''
````
**Response**
```
{
    {
    "message": "loaded successfully",
    "data": {
        "id": 1,
        "createDate": "2022-11-21T16:30:07.781286",
        "updateDate": "2022-11-21T16:31:14.944319",
        "serialNumber": "DJI_MAVIC03_AIR_2",
        "weight": 120.0,
        "model": "LIGHTWEIGHT",
        "state": "LOADED",
        "batteryCapacity": 100.00,
        "medication": [
            {
                "id": 1,
                "name": "Paracetamol",
                "weight": 34.0,
                "code": "PC003",
                "image": "image01.jpeg",
                "drone": null
            }
        ]
    }
}
```
---
**checking loaded medication items for a given drone**
- Endpoint: '(http://localhost:8080/api/loaded-medication/DJI_MAVIC03_AIR_2)';
- Path Variables:
   - DJI_MAVIC03_AIR_2 : serialNumber;
**Payload**
```
curl --location --request GET '(http://localhost:8080/api/loaded-medication/DJI_MAVIC03_AIR_2)'
````
**Response**
```
{
    "message": "success",
    "timesStamp": "2022-08-22T05:43:05.876705",
    "serialNumber": "DJI_MAVIC03_AIR_2",
    "medications": [
        {
            "code": "PC003",
            "name": "Paracetamol",
            "weight": 50,
            "image": "image01.jpeg"
        }
    ]
}
```
---
**Getting Available Drones For Loading**
- Endpoint: '(http://localhost:8080/api/available-drone)';
- Assumption:
   - Drones are available for loading if their battery capacity is above 25%;
   - Drones are available for loading if their loading state is IDLE
**Payload**
```
curl --location --request GET '(http://localhost:8080/api/available-drone)'
````
**Response**
```
{
    "message": "status",
    "data": [
        {
            "id": 1,
            "createDate": "2022-11-21T16:30:07.781286",
            "updateDate": "2022-11-21T16:30:07.781502",
            "serialNumber": "DJI_MAVIC03_AIR_2",
            "weight": 120.0,
            "model": "LIGHTWEIGHT",
            "state": "IDLE",
            "batteryCapacity": 100.00,
            "medication": []
        },
        {
            "id": 2,
            "createDate": "2022-11-21T16:30:08.001985",
            "updateDate": "2022-11-21T16:30:08.002029",
            "serialNumber": "DJI_MAVIC05_AIR_3",
            "weight": 200.8,
            "model": "CRUISERWEIGHT",
            "state": "IDLE",
            "batteryCapacity": 34.00,
            "medication": []
        },
        {
            "id": 5,
            "createDate": "2022-11-21T16:30:08.014965",
            "updateDate": "2022-11-21T16:30:08.01501",
            "serialNumber": "DJI_MAVIC01_AIR_1",
            "weight": 130.5,
            "model": "LIGHTWEIGHT",
            "state": "IDLE",
            "batteryCapacity": 75.00,
            "medication": []
        },
        {
            "id": 9,
            "createDate": "2022-11-21T16:30:08.028716",
            "updateDate": "2022-11-21T16:30:08.028756",
            "serialNumber": "DSF_MAVIC08_AIR_2",
            "weight": 50.07,
            "model": "CRUISERWEIGHT",
            "state": "IDLE",
            "batteryCapacity": 55.00,
            "medication": []
        },
        {
            "id": 10,
            "createDate": "2022-11-21T16:30:08.031822",
            "updateDate": "2022-11-21T16:30:08.031906",
            "serialNumber": "AMP_MAVIC29_AIR_2",
            "weight": 34.03,
            "model": "HEAVYWEIGHT",
            "state": "IDLE",
            "batteryCapacity": 35.00,
            "medication": []
        }
    ]
}
```
---
**Check Battery Level for a given Drone**
- Endpoint: --request GET '(http://localhost:8080/api/battery-level/DJI_MAVIC03_AIR_2)';
**Payload**
```
curl --location --request GET '(http://localhost:8080/api/battery-level/DJI_MAVIC03_AIR_2)'
````
**Response**
```
{
    "message": "Drone with serialNumberDJI_MAVIC03_AIR_2:",
    "data": 100.00
}
```
---
**Documentation**
http://localhost:8080/swagger-ui.html
