import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Category } from '../../models/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http:HttpClient) { }
 
   deleteCategory(id:number){
     return this.http.delete<Category[]>("http://localhost:8080/api/admin/categories/delete/"+id);
   }
 
   createCategory(obj:Category){
     return this.http.post<Category[]>("http://localhost:8080/api/admin/categories/create",obj);
   }

   updateCategory(obj:Category,id:number){
    return this.http.put<Category[]>("http://localhost:8080/api/admin/categories/update/"+id,obj);
   }
}
