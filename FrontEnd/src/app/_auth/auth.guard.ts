import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router =inject(Router);
  const token=localStorage.getItem('loginUserToken');
 
  const role=localStorage.getItem('loginUserRole');
  const roleWanted=route.data['role'];
 
  if(token!=null)
  {
    if(role==roleWanted)
    {
      return true;
    }
    else{
      localStorage.clear();
      router.navigate(['login'])
      return false;
    }
  }
  else{
    localStorage.clear();
    router.navigate(['login']);
    return false;
  }
};
