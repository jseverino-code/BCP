import { Component, OnInit } from '@angular/core';
import { IConversor } from 'src/app/models/IConversor';
import { IMonedas } from 'src/app/models/IMonedas';
import { ConversorService } from 'src/app/services/conversor.service';
import { DateAdapter } from '@angular/material/core';
import { FormControl } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { IConversorResponse } from 'src/app/models/IConversorResponse';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.scss']
})
export class PrincipalComponent implements OnInit {
  
  model: IConversor = {
    monto: '',
    monedaOrigen: '',
    monedaDestino: '',
    fecha: ''
  }

  selectedMonedaDesdeValue!:string;
  selectedMonedaDestinoValue!:string;
  fechaCotizacion = new Date();
  MostrarResultado = false;

  lstMonedas!: Array<IMonedas>;
  respuestaProcesada!: IConversorResponse;

  constructor(private conversorServ: ConversorService, 
              private dateAdapter: DateAdapter<Date>,
              private datePipe: DatePipe
              ) {
    this.dateAdapter.setLocale('es-AR'); //dd/MM/yyyy
   }

  ngOnInit(): void {
    this.conversorServ.ObtenerMonedas()
      .subscribe((s: Array<IMonedas>)=>{
        this.lstMonedas = s;
      },
      (err)=> {
        console.log('Error:' + err);
      }) ;   
  }

  Calcular()
  {  
    this.MostrarResultado = false;
    //Convierto la fecha y la asigno
    this.model.fecha = <string>this.datePipe.transform(new Date(this.fechaCotizacion), 'yyyy-MM-dd');
   
    this.conversorServ.ProcesarConversor(this.model)
        .subscribe(
          (s:IConversorResponse)=>{
              this.respuestaProcesada = s;
              this.MostrarResultado = true;
          },
          (err)=>{
              console.log('Error:' + err);
          }
    );
  }
}