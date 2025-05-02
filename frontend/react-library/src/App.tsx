import React from 'react';
import './App.css';
import {Navbar} from "./layout/NavbarAndFooter/Navbar";
import {ExploreTopBooks} from "./layout/HomePage/ExploreTopBooks";
import {Carousel} from "./layout/HomePage/Carousel";

function App() {
  return (
      <div>
          <Navbar/>
          <ExploreTopBooks/>
          <Carousel/>
      </div>
  );
}

export default App;
