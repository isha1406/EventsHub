import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, map } from 'rxjs';
import Swal from 'sweetalert2';
import { Category } from '../../../models/category';
import { CategoryService } from '../../../services/category/category.service';
import { EventService } from '../../../services/event/event.service';

@Component({
  selector: 'app-categories',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './categories.component.html',
  styleUrl: './categories.component.css'
})
export class CategoriesComponent implements OnInit {

  categoryList: Category[] = [];

  categoryObj: any = {
    "categoryId": 0,
    "categoryName": "",
  }

  isSidePanelVisible: boolean = false;
  resetVisible: boolean = true;
  newString: boolean = true;

  ngOnInit(): void {
    this.getCategories();
  }


  constructor(private catSrv: CategoryService, private eventsSrv: EventService,private router: Router) {
  }


  getCategories() {
    this.eventsSrv.getAllCategory().subscribe(
      (res) => {
        console.log(res);
        this.categoryList = res;
        console.log(this.categoryList);
      }
    );
  }



  onUpdate() {
    if (this.categoryObj.categoryName == '')
      Swal.fire("Category Name Required!");
    // alert("Category Name Required!")

    else {
      // this.catSrv.updateCategory(this.categoryObj, this.categoryObj.categoryId).subscribe(
      //   (res: any) => {
      //     if (res) {
      //       // alert("Category Updated");
      //       Swal.fire("Category Updated");
      //       this.isSidePanelVisible=false;
      //       this.getCategories();
      //     }
      //   }
      // )
      Swal.fire({
        title: "Do you want to save the changes?",
        showDenyButton: true,
        showCancelButton: true,
        confirmButtonText: "Save",
        denyButtonText: `Don't save`
      }).then((result) => {
        if (result.isConfirmed) {
          this.catSrv.updateCategory(this.categoryObj, this.categoryObj.categoryId).subscribe(
            (res: any) => {
              if (res) {
                // alert("Category Updated");
                // Swal.fire("Category Updated");
                this.isSidePanelVisible = false;
                this.getCategories();
              }
            }
          )
          Swal.fire("Saved!", "", "success");
        } else if (result.isDenied) {
          Swal.fire("Changes are not saved", "", "info");
          this.isSidePanelVisible = false;
          this.getCategories();
        }
      });
    }
  }

  onSave() {
    if (this.categoryObj.categoryName == '')
      Swal.fire("Category Name Required!");
    // alert("Category Name Required!")
    else {
      this.catSrv.createCategory(this.categoryObj).subscribe(
        (res: any) => {
          if (res) {
            console.log(res);
            Swal.fire("Category Added");
            // alert("Category Added");
            this.isSidePanelVisible = false;
            this.getCategories();
            // this.onReset();
          }
        }
      )
    }
  }

  onReset() {
    this.categoryObj = {
      "categoryId": 0,
      "categoryName": "",
    }
  }

  onEdit(item: Category) {
    this.categoryObj = item;
    this.openSidePanel();
    this.newString = false;
    this.resetVisible = false;
  }

  onDelete(item: any) {
    // const isDelete = confirm("Are you sure?");
    // if (isDelete) {
    //   this.catSrv.deleteCategory(item.categoryId).subscribe(
    //     (res: any) => {
    //       if (res) {
    //         // window.location.reload();
    //         this.router.navigate(['/category']);
    //          this.getCategories();
    //       }
    //     }
    //   )
    // }
    Swal.fire({
      title: "Are you sure?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
          this.catSrv.deleteCategory(item.categoryId).subscribe(
        (res: any) => {
          console.log(res);
        }
      )
        // Swal.fire({
        //   title: "Deleted!",
        //   text: "Your file has been deleted.",
        //   icon: "success"
        // });
        // this.router.navigate(['/category']);
      }
    });
  }



  openSidePanel() {
    this.isSidePanelVisible = true;
    this.newString = true;
    this.resetVisible = true;
  }

  closeSidePanel() {
    this.isSidePanelVisible = false;
    this.onReset();
  }
}
