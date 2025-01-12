import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {HttpClient, HttpResponse, HttpHeaders} from "@angular/common/http";
import {Observable} from 'rxjs';
import {map} from "rxjs/operators";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private httpClient: HttpClient) {
  }

  private baseURL: string = 'http://localhost:8080';

  private getUrl: string = this.baseURL + '/room/reservation/v1/';
  private postUrl: string = this.baseURL + '/room/reservation/v1';
  public submitted!: boolean;
  roomsearch!: FormGroup;
  rooms!: Room[];
  request!: ReserveRoomRequest;
  currentCheckInVal!: string;
  currentCheckOutVal!: string;
  public welcomeMessage: String[] = [];
  public presentationTimes: string[] = [];

  ngOnInit() {
    this.roomsearch = new FormGroup({
      checkin: new FormControl(' '),
      checkout: new FormControl(' '),
    });

    /*TODO Retrieve Welcome Message & Assign to Array */
    this.getWelcomeMessage().subscribe((message) => {
      this.welcomeMessage = message;
    });

    //     this.rooms=ROOMS;

    const roomsearchValueChanges$ = this.roomsearch.valueChanges;

    // subscribe to the stream
    roomsearchValueChanges$.subscribe(x => {
      this.currentCheckInVal = x.checkin;
      this.currentCheckOutVal = x.checkout;
    });


    /*TODO: Retrieve the presentation times on component initialization */
    this.getPresentationTimes().subscribe(times => {
      this.presentationTimes = times;
    })
  }

  /*TODO: Fetch the time zone data via HTTP GET */
  getPresentationTimes(): Observable<string[]> {
    return this.httpClient.get<string[]>(this.baseURL + '/api/presentation-time');
  }

  /*TODO: Fetch Messages */
  getWelcomeMessage(): Observable<String[]> {
    return this.httpClient.get<String[]>(this.baseURL + '/api/welcome');
  }


  onSubmit({value, valid}: { value: Roomsearch, valid: boolean }) {
    /* USD to CAD rate */
    const usdToCadRate = 1.36831;
    /* USD to EUR rate */
    const usdToEurRate = 0.929293;

    this.getAll().subscribe(
      rooms => {
        console.log(Object.values(rooms)[0]);
        this.rooms = <Room[]>Object.values(rooms)[0];


        /*TODO: Calculate prices in CAD & EUR for each room */
        this.rooms.forEach(room => {
          const priceUSD = parseFloat(room.price);
          /* Convert to CAD */
          room.priceCAD = (priceUSD * usdToCadRate).toFixed(2);
          /* Convert to EUR */
          room.priceEUR = (priceUSD * usdToEurRate).toFixed(2);
        });
      }
    );
  }

  reserveRoom(value: string) {
    this.request = new ReserveRoomRequest(value, this.currentCheckInVal, this.currentCheckOutVal);

    this.createReservation(this.request);
  }

  createReservation(body: ReserveRoomRequest) {
    let bodyString = JSON.stringify(body); // Stringify payload
    let headers = new Headers({'Content-Type': 'application/json'}); // ... Set content type to JSON
    // let options = new RequestOptions({headers: headers}); // Create a request option

    const options = {
      headers: new HttpHeaders().append('key', 'value'),

    }

    this.httpClient.post(this.postUrl, body, options)
      .subscribe(res => console.log(res));
  }

  /*mapRoom(response:HttpResponse<any>): Room[]{
    return response.body;
  }*/

  getAll(): Observable<any> {


    return this.httpClient.get(this.baseURL + '/room/reservation/v1?checkin=' + this.currentCheckInVal + '&checkout=' + this.currentCheckOutVal, {responseType: 'json'});
  }

}


export interface Roomsearch {
  checkin: string;
  checkout: string;
}


export interface Room {
  id: string;
  roomNumber: string;
  price: string;
  links: string;
  /* B2 Prices */
  priceCAD?: string;
  priceEUR?: string;

}

export class ReserveRoomRequest {
  roomId: string;
  checkin: string;
  checkout: string;

  constructor(roomId: string,
              checkin: string,
              checkout: string) {

    this.roomId = roomId;
    this.checkin = checkin;
    this.checkout = checkout;
  }
}

/*
var ROOMS: Room[]=[
  {
  "id": "13932123",
  "roomNumber" : "409",
  "price" :"20",
  "links" : ""
},
{
  "id": "139324444",
  "roomNumber" : "509",
  "price" :"30",
  "links" : ""
},
{
  "id": "139324888",
  "roomNumber" : "609",
  "price" :"40",
  "links" : ""
}
] */

