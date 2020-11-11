<template>

  <div class="home">
  <button type="button" class="btn btn-primary" id="fixedbutton">+</button>
  <section id="mainArtSection">
    <hooper id="hooperContainer" :autoPlay="true" :playSpeed="70000" itemsToShow="1">
    <slide>
      <div class="sectionImage" id="mainHomeArt1"></div>
    </slide>
    <slide>
      <div class="sectionImage" id="mainHomeArt2"></div>
    </slide>
    <slide>
      <div class="sectionImage" id="mainHomeArt3"></div>
    </slide>
    <slide>
      <div class="sectionImage" id="mainHomeArt4"></div>
    </slide>
    </hooper>
  <div class="sectionContent" id="sectionContentTitle">Vis Art</div>
</section>
<section id="tagSection">
<div id="tagContainer">
 <hooper :settings="hooperSettings">
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Acrylic</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Sculpture</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Cubism</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Realism</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Fauvism</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Oil</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Absurd</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Comtemporary</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Resin</button>
    </slide>
    <slide>
    <button type="button" class="btn btn-primary tagBtn">Glass</button>
    </slide>
  </hooper>
</div>
</section>
    <div class="listingContainer">
      <h1> – Featured Art – </h1>
      <div class="card-columns">
  <div class="card shadow-sm ">
    <img class="card-img-top cardImg" src="../assets/cardTrial.png" alt="Card image cap">
    <div class="sectionContent sectionContentListing">Title of Painting</div>
    <div class="card-body">
      <h4 class="card-title"><a href="/artists">Picasso</a></h4>
      <h5 class="card-title">$10000</h5>
      <p class="card-text">This is a description of the painting. The painting is described by this description. The description describes the painting</p>
    </div>
  </div>
  <div class="card shadow-sm">
    <img class="card-img-top cardImg" src="../assets/cardTrial0.png" alt="Card image cap">
    <div class="sectionContent sectionContentListing">Title of Painting</div>
    <div class="card-body">
      <h4 class="card-title"><a href="/artists">Picasso</a></h4>
      <h5 class="card-title">$4000</h5>
      <p class="card-text">This is a description of the painting. The painting is described by this description. The description describes the painting</p>
    </div>
  </div>
  <div class="card shadow-sm">
    <img class="card-img-top cardImg" src="../assets/cardTrial3.png" alt="Card image cap">
    <div class="sectionContent sectionContentListing">Title of Painting</div>
    <div class="card-body">
      <h4 class="card-title"><a href="/artists">Picasso</a></h4>
      <h5 class="card-title">$15000</h5>
      <p class="card-text">This is a description of the painting. The painting is described by this description. The description describes the painting</p>
    </div>
  </div>
  <div class="card shadow-sm">
    <img class="card-img-top cardImg" src="../assets/cardTrial2.png" alt="Card image cap">
    <div class="sectionContent sectionContentListing">Title of Painting</div>
    <div class="card-body">
      <h4 class="card-title"><a href="/artists">Picasso</a></h4>
      <h5 class="card-title">$55000</h5>
      <p class="card-text">This is a description of the painting. The painting is described by this description. The description describes the painting</p>
    </div>
  </div>
  <div class="card shadow-sm">
    <img class="card-img-top cardImg" src="../assets/cardTrial1.png" alt="Card image cap">
    <div class="sectionContent sectionContentListing">Title of Painting</div>
    <div class="card-body">
      <h4 class="card-title"><a href="/artists">Picasso</a></h4>
      <h5 class="card-title">$7999</h5>
      <p class="card-text">This is a description of the painting. The painting is described by this description. The description describes the painting</p>
    </div>
  </div>
  <div class="card shadow-sm">
    <img class="card-img-top cardImg" src="../assets/cardTrial4.png" alt="Card image cap">
    <div class="sectionContent sectionContentListing">Title of Painting</div>
    <div class="card-body">
      <h4 class="card-title"><a href="/artists">Picasso</a></h4>
      <h5 class="card-title">$900</h5>
      <p class="card-text">This is a description of the painting. The painting is described by this description. The description describes the painting</p>
    </div>
  </div>
</div>
    </div>
    <div>
    </div>
  </div>
</template>
<script>
// @ is an alias to /src
//  bar scroll
import { Hooper, Slide } from 'hooper'
import 'hooper/dist/hooper.css'
export default {
  name: 'Home',
  components: {
    Hooper,
    Slide
  },
  data () {
    return {
      hooperSettings: {
        itemsToShow: 8,
        centerMode: true,
        infiniteScroll: true,
        itemsToSlide: 8,
        rtl: true,
        pagination: 'no'
      }
    }
  }
}
</script>
