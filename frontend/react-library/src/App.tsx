import React from 'react';
import './App.css';
import {Navbar} from "./layout/NavbarAndFooter/Navbar";
import {Footer} from "./layout/NavbarAndFooter/Footer";
import {HomePage} from "./layout/HomePage/HomePage";

export const  App = () => {
  return (
      <div>
          <Navbar/>
          <HomePage/>
          <Footer/>
      </div>
  );
}
