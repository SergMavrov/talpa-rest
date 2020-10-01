Hello, dear Talpa code-reviewer.

I have prepared the web-service based on REST which will return for you response containing best routes.

I have prepared the start.Unix.sh script in the root of the project. Run it please, it will build the project
with tests and start the service on the port 8080. Open your browser http://localhost:8080/talpa/swagger-ui.html.
There you can try the POST method /routes using the button "Try it out"

The test data must be put in request body:

[
  {
    "name": "Depot1",
    "address": "Metrostrasse 12, 40235 Düsseldorf",
    "latitude": 51.234749,
    "longitude": 6.825345,
    "type": "DEPOT"
  },
  {
    "name": "Depot2",
    "address": "Ludenberger Str. 1, 40629 Düsseldorf",
    "latitude": 51.241368,
    "longitude": 6.830328,
    "type": "DEPOT"
  },
  {
    "name": "Store1",
    "address": "Willstätterstraße 24, 40549 Düsseldorf",
    "latitude": 51.239645,
    "longitude": 6.719469,
    "type": "STORE"
  },
  {
    "name": "Store2",
    "address": "Bilker Allee 128, 40217 Düsseldorf",
    "latitude": 51.210891,
    "longitude": 6.774425,
    "type": "STORE"
  },
  {
    "name": "Store3",
    "address": "Hammer Landstraße 113, 41460 Neuss",
    "latitude": 51.2035,
    "longitude": 6.72116,
    "type": "STORE"
  },
  {
    "name": "Store4",
    "address": "Gladbacher Str. 471, 41460 Neuss",
    "latitude": 51.22547,
    "longitude": 6.69055,
    "type": "STORE"
  },
  {
    "name": "Store5",
    "address": "Lise-Meitner-Straße 1, 40878 Ratingen",
    "latitude": 51.29575,
    "longitude": 6.8301,
    "type": "STORE"
  },
  {
    "name": "C1",
    "address": "Friedrichstraße 133, 40217 Düsseldorf",
    "latitude": 51.210065,
    "longitude": 6.776541,
    "type": "CUSTOMER"
  },
  {
    "name": "C2",
    "address": "Fischerstraße 23, 40477 Düsseldorf",
    "latitude": 51.236674,
    "longitude": 6.777167,
    "type": "CUSTOMER"
  },
    {
      "name": "C3",
      "address": "Wildenbruchstraße 2, 40545 Düsseldorf",
      "latitude": 51.22942,
      "longitude": 6.74976,
      "type": "CUSTOMER"
    },
     {
       "name": "C4",
       "address": "Reisholzer Str. 48, 40231 Düsseldorf",
       "latitude": 51.206921,
       "longitude": 6.832382,
       "type": "CUSTOMER"
     }
]

You can prepare any test data would you like to see.

Also in the root of the project you can find jpg file which demonstrates the position of location directly
on the geo map. It is useful to evaluate the solution.

Thanks,
Sergii