import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IConversor } from '../models/IConversor';
import { IConversorResponse } from '../models/IConversorResponse';
import { IMonedas } from '../models/IMonedas';

@Injectable({
  providedIn: 'root'
})
export class ConversorService {
  private api_url: string = "http://localhost:5001/api"
  constructor(private _http: HttpClient) { }

  ObtenerMonedas(): Observable<Array<IMonedas>> 
  {
    return this._http.get<Array<IMonedas>>(this.api_url + '/monedas')
  }

  ProcesarConversor(solicitud: IConversor): Observable<IConversorResponse> 
  {
    return this._http.post<IConversorResponse>(this.api_url + "/cambios/conversiones", solicitud );
  }
}
