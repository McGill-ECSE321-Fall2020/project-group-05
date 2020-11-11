<template>

  <div class="home">
  <button type="button" class="btn btn-primary" id="fixedbutton">+</button>
  <section id="mainArtSection">
  <div class="sectionImage" id="mainHomeArt"></div>
  <div class="sectionContent" id="sectionContentTitle">Vis Art</div>
</section>
<section id="tagSection">
<button type="button" class="btn btn-primary tagBtn">Acrylic</button>
<button type="button" class="btn btn-primary tagBtn">Sculpture</button>
<button type="button" class="btn btn-primary tagBtn">Cubism</button>
<button type="button" class="btn btn-primary tagBtn">Contemporary</button>
<button type="button" class="btn btn-primary tagBtn">Mixed</button>
<button type="button" class="btn btn-primary tagBtn">Realism</button>
<button type="button" class="btn btn-primary tagBtn">Fauvism</button>
<button type="button" class="btn btn-primary tagBtn">Oil</button>
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
      <h4 class="card-title">Boticelli</h4>
      <h5 class="card-title">$4000</h5>
      <p class="card-text">This is a description of the painting. The painting is described by this description. The description describes the painting</p>
    </div>
  </div>
  <div class="card shadow-sm">
    <img class="card-img-top cardImg" src="../assets/cardTrial3.png" alt="Card image cap">
    <div class="sectionContent sectionContentListing">Title of Painting</div>
    <div class="card-body">
      <h4 class="card-title">Renoir</h4>
      <h5 class="card-title">$15000</h5>
      <p class="card-text">This is a description of the painting. The painting is described by this description. The description describes the painting</p>
    </div>
  </div>
  <div class="card shadow-sm">
    <img class="card-img-top cardImg" src="../assets/cardTrial2.png" alt="Card image cap">
    <div class="sectionContent sectionContentListing">Title of Painting</div>
    <div class="card-body">
      <h4 class="card-title">Van Gogh</h4>
      <h5 class="card-title">$55000</h5>
      <p class="card-text">This is a description of the painting. The painting is described by this description. The description describes the painting</p>
    </div>
  </div>
  <div class="card shadow-sm">
    <img class="card-img-top cardImg" src="../assets/cardTrial1.png" alt="Card image cap">
    <div class="sectionContent sectionContentListing">Title of Painting</div>
    <div class="card-body">
      <h4 class="card-title">Author</h4>
      <h5 class="card-title">Price</h5>
      <p class="card-text">This is a description of the painting. The painting is described by this description. The description describes the painting</p>
    </div>
  </div>
  <div class="card shadow-sm">
    <img class="card-img-top cardImg" src="../assets/cardTrial4.png" alt="Card image cap">
    <div class="sectionContent sectionContentListing">Title of Painting</div>
    <div class="card-body">
      <h4 class="card-title">Author</h4>
      <h5 class="card-title">Price</h5>
      <p class="card-text">This is a description of the painting. The painting is described by this description. The description describes the painting</p>
    </div>
  </div>
</div>
    </div>
    <div>
    </div>
    <HelloWorld msg="Welcome to Your Vue.js App" />
  </div>
</template>
<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'Home',
  components: {
    HelloWorld
  }
}
</script>
