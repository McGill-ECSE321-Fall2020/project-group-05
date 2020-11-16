<template>
  <div id="app">
    <nav class="navbar navbar-expand-lg navbar-light">
      <a class="navbar-brand" href="/"
        ><img id="navlogo" src="./assets/navlogo0.png" alt="nav"
      /></a>
      <button
        class="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item active">
            <a class="nav-link" href="/"
              >Home <span class="sr-only">(current)</span></a
            >
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/about">About</a>
          </li>
        </ul>
        <form
          method="get"
          action="/search/"
          class="form-inline my-2 my-lg-0"
          id="searchForm"
        >
          <input
            class="form-control mr-sm-2"
            type="search"
            placeholder="Separate by a comma ','"
            aria-label="Search"
            name="keywords"
          />
          <button class="btn my-2 my-sm-0" type="submit">
            <img id="searchIcon" src="./assets/loupe.png" alt="Search" />
          </button>
        </form>
        <div v-if="!isSignedIn">
        <ul class="navbar-nav ml-auto" id="loginUl">
          <li class="nav-item loginDiv">
            <a class="nav-link" href="/login">Login</a>
          </li>
          <li class="nav-item loginDiv">
            <a class="nav-link" href="/signup">Sign Up</a>
          </li>
        </ul>
        </div>
        <div v-else>
        <form class="form-inkine">
          <button class="btn btn-light my-2 my-sm-0" type="button" v-on:click="unauthenticateUser">Logout</button>
        </form>
        </div>
      </div>
    </nav>
    <router-link to="/artlisting/create"
      ><button v-show="isArtistLoggedIn" type="button" class="btn btn-primary hvr-bob" id="fixedbutton">
        +
      </button></router-link
    >
    <router-view />
  </div>
</template>
<script>
/* eslint-disable */
var backend = require("@/tools/backend");
export default {
  data: function() {
    return {
      isArtistLoggedIn: false,
      isSignedIn: false
    };
  },
  created: function() {
    let vm = this;
    backend.onFirebaseAuth(function(user) {
      if (user != null) {
        backend.get('users/get/'+user.uid).then(resp => {
          if (resp.data.role == "Customer") {
            backend.get('customers/get/'+user.uid).then(resp => {
              vm.isArtistLoggedIn = true
            })
          } else {
             vm.isArtistLoggedIn = false
          }
        })
        vm.isSignedIn = true;
      } else {
        vm.isSignedIn = false;
        vm.isArtistLoggedIn = false
      }
    });
  },
  methods: {
    unauthenticateUser: function() {
      backend.unauthenticate();
      setTimeout(()=>this.$router.push({path:'/login'}), 100);
  }}
};
</script>

<style>
/*
  Icons made by <a href="https://www.flaticon.com/authors/kiranshastry" title="Kiranshastry">Kiranshastry</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
*/
.descriptionContainer {
  margin-top: 5%;
  max-height: 300px;
  overflow: hidden;
  float: left;
}
#hooperContainer {
  width: 100%;
  height: 100%;
}
.hooperContainerCardImg {
}
.hooperContainerImg {
  width: 2px;
  height: 2px;
  margin: 0;
  padding: 0;
}

.navbar {
  background-color: #fff !important;
  position: fixed !important; /* fixing the position takes it out of html flow - knows
                   nothing about where to locate itself except by browser
                   coordinates */
  left: 0 !important; /* top left corner should start at leftmost spot */
  top: 0 !important; /* top left corner should start at topmost spot */
  width: 100vw !important; /* take up the full browser width */
  z-index: 200 !important; /* high z index so other content scrolls underneath */
}
.mainArtContainer {
  height: 532px;
}
.mainArt {
  background-size: 100% auto;
}
#mainArtSection {
  background: #444;
  position: relative;
  margin: auto;
  height: 385px;
  width: 100%;
  color: #fff;
}
#tagSection {
  background: rgb(202, 193, 176);
  position: relative;
  margin: 0;
  padding: 0;
  height: 6em !important;
  width: 100%;
  color: #fff;
  padding-top:5px;
}
.mainTagsTitle{
margin-bottom:-5px;
color: rgb(255, 255, 255);
}
#hooperContainerTags {
  height: 60px;
}
.sectionImage {
  width: 100%;
  height: 100%;
  background: transparent none no-repeat top top;
  background-size: cover;
}
.sectionContent {
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
}
#sectionContentTitle {
  font-size: 170px;
  top: 50%;
}
.sectionContentListing {
  color: rgb(43, 38, 32);
  font-size: 150%;
  font-weight: 400;
  border: 1px;
  width:100%;
  padding:0%;
  padding-top: 4%!important;
  height:12% !important;
  overflow: hidden;
  transition: all .2s ease-in-out;
  z-index: 1000;
}
.listingsTitle {
  color: rgb(43, 38, 32);
  margin: auto;
  margin-top: 20px;
}
#listingsTitle {
  color: rgb(43, 38, 32);
  margin: auto;
}
#tagContainer {
  width: 80%;
  margin: auto;
  overflow-x: scroll;
  white-space: nowrap;
  padding: 0;
}
.cardImg {
  opacity: 1;
  width: cover !important;
  height: cover !important;
  overflow: hidden !important;
  padding: 0px;
  margin: 0px;
  transition: transform .2s; /* Animation */
  min-height: 20em;
}
.cardImg:hover {
  opacity: 1;
  transform: scale(1.08, 1.08) !important;
}

.sectionContentListing:hover{
  font-size: 160% !important;
  color: rgb(90, 79, 66) !important;
  cursor: pointer;
}

.hooperContainerCardImg:hover ~ .cardImg{
  opacity: 1;
}
.cardTitlesContainer {
  height: 10% !important;
  overflow: hidden;
}
#fixedbutton {
  position: fixed;
  bottom: 5%;
  right: 5%;
  z-index: 9998;
  background-color: rgb(202, 121, 110);
  font-size: 200%;
  font-weight: 600;
  border-radius: 50%;
  height: 70px;
  width: 70px;
  border-width: 0px;
}
/* Bob */
@-webkit-keyframes hvr-bob {
  0% {
    -webkit-transform: translateY(-8px);
    transform: translateY(-8px);
  }
  50% {
    -webkit-transform: translateY(-4px);
    transform: translateY(-4px);
  }
  100% {
    -webkit-transform: translateY(-8px);
    transform: translateY(-8px);
  }
}
@keyframes hvr-bob {
  0% {
    -webkit-transform: translateY(-8px);
    transform: translateY(-8px);
  }
  50% {
    -webkit-transform: translateY(-4px);
    transform: translateY(-4px);
  }
  100% {
    -webkit-transform: translateY(-8px);
    transform: translateY(-8px);
  }
}
@-webkit-keyframes hvr-bob-float {
  100% {
    -webkit-transform: translateY(-8px);
    transform: translateY(-8px);
  }
}
@keyframes hvr-bob-float {
  100% {
    -webkit-transform: translateY(-8px);
    transform: translateY(-8px);
  }
}
.hvr-bob {
  display: inline-block;
  vertical-align: middle;
  -webkit-transform: perspective(1px) translateZ(0);
  transform: perspective(1px) translateZ(0);
  box-shadow: 0 0 1px rgba(0, 0, 0, 0);
}
.hvr-bob:hover,
.hvr-bob:focus,
.hvr-bob:active {
  -webkit-animation-name: hvr-bob-float, hvr-bob;
  animation-name: hvr-bob-float, hvr-bob;
  -webkit-animation-duration: 0.3s, 1.5s;
  animation-duration: 0.3s, 1.5s;
  -webkit-animation-delay: 0s, 0.3s;
  animation-delay: 0s, 0.3s;
  -webkit-animation-timing-function: ease-out, ease-in-out;
  animation-timing-function: ease-out, ease-in-out;
  -webkit-animation-iteration-count: 1, infinite;
  animation-iteration-count: 1, infinite;
  -webkit-animation-fill-mode: forwards;
  animation-fill-mode: forwards;
  -webkit-animation-direction: normal, alternate;
  animation-direction: normal, alternate;
}
#mainHomeArt1 {
  background-image: url(https://www.slantmagazine.com/wp-content/uploads/2010/05/picassoandbraquegotothemovies.jpg);
}
#mainHomeArt2 {
  background-image: url(https://media.architecturaldigest.com/photos/5ab021fad91fc303af543aa3/16:9/w_2560%2Cc_limit/56797527.jpg);
}
#mainHomeArt3 {
  background-image: url(https://thisiscolossal.com/wp-content/uploads/2011/01/odani-5.jpg);
}
#mainHomeArt4 {
  background-image: url(https://static01.nyt.com/images/2011/04/05/opinion/editorial_tooker/editorial_tooker-jumbo.jpg);
}
#navLogo {
  height: auto;
  width: auto;
  max-height: 72px;
  max-width: 250px;
}
.toggleBtnHome {
  border: 0px;
  background-color: #fff;
  color: rgb(148, 126, 98);
}
.toggleBtnHome:hover {
  color: rgb(43, 38, 32);
}
.tagBtn {
  margin-top: 10px;
  margin-bottom: 10px;
  margin-left: 5px;
  margin-right: 5px;
  padding: 0px;
  height: 40px;
  width: 95%;
  border-color: rgb(231, 215, 191) !important;
  background-color: #fff !important;
  color: rgb(146, 135, 113) !important;
}
.tagBtn:hover {
  background-color: rgb(153, 153, 131) !important;
  color: rgb(226, 226, 220) !important;
  border-color: rgb(163, 155, 142) !important;
}
#listingContainer {
  padding: 75px;
  background-color: #fff;
  -webkit-animation: fadein 2s; /* Safari, Chrome and Opera > 12.1 */
  -moz-animation: fadein 2s; /* Firefox < 16 */
  -ms-animation: fadein 2s; /* Internet Explorer */
  -o-animation: fadein 2s; /* Opera < 12.1 */
  animation: fadein 2s;
}

@keyframes fadein {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

/* Firefox < 16 */
@-moz-keyframes fadein {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

/* Safari, Chrome and Opera > 12.1 */
@-webkit-keyframes fadein {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

/* Internet Explorer */
@-ms-keyframes fadein {
    from { opacity: 0; }
    to   { opacity: 1; }
}
.homeCard{
  width:90% !important;
  margin:30px !important;
  border:1px solid rgb(128, 116, 116);
  position: relative;
}
.homeCard:after{
    content  : "";
  position : absolute;
  z-index  : 1;
  bottom   : 0;
  left     : 0;
  pointer-events   : none;
  background-image : linear-gradient(to bottom, 
                    rgba(255,255,255, 0), 
                    rgba(255,255,255, 1) 95%);
  width    : 100%;
  height   : 4em;
}
.card-columns-home{
  margin:auto !important;
}
.cardArtist{
position: absolute;
float: left;
font-size: 120%;

}
.cardPrice{
  float: right;
font-size: 120%;
margin-top:17%;
margin-bottom:5%;
margin-right: 1%;
}
.cardArtist{
 color: rgb(202, 182, 145);
 transition: all .2 ease-in-out;
margin-top:15%;
margin-bottom:5%;
margin-left: 5%;
}
.cardArtist:hover{
  color: rgb(145, 123, 84);
  cursor: pointer;
}
.cardDesc{
float:left;
font-size: 100%;
margin-top:5%;
margin-bottom:1em;
width:100%;
max-height:6rem;
overflow:hidden;
padding-bottom: 2.5rem;
}
.cardBody{
  height:120%;
  position:relative;
  padding:0px !important;
}
#searchForm{
position: absolute;
left: 50%;
transform: translatex(-50%);
}
#searchIcon{
  transform:scale(0.9,0.9);
  padding: 0px;
  margin: 0px;
  max-height: 30px;
  max-width: 30px;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 65px;
}

#nav {
  padding: 10px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
/*
card scaling
*/
@media (min-width: 36em) {
  .card-columns {
    -webkit-column-count: 1 !important;
    -moz-column-count: 1 !important;
    column-count: 1 !important;
  }
}
@media (min-width: 50em) {
  .card-columns {
    -webkit-column-count: 2 !important;
    -moz-column-count: 2 !important;
    column-count: 2 !important;
  }
}
@media (min-width: 55em) {
  .card-columns {
    -webkit-column-count: 3 !important;
    -moz-column-count: 3 !important;
    column-count: 3 !important;
  }
}
@media (min-width: 62em) {
  .card-columns {
    -webkit-column-count: 4;
    -moz-column-count: 4;
    column-count: 4;
  }
}
@media (min-width: 75em) {
    .card-columns {
        -webkit-column-count: 5;
        -moz-column-count: 5;
        column-count: 5;
    }
}
.formContainerCreate{
  width:80%;
  height:40em;
 background-color: #dabb86;
background-image: linear-gradient(45deg, #dabb86 0%, #e9b1a3 100%);
padding-top:0.4em ;
border-radius: 50px;
}
.createlisting{
  display:flex;
  justify-content: center;
  align-items: center;
}
#publishTitle{
  margin:0.4em;
  font-size:42px !important;
  color: white;
}
input{
  border: 3px solid white;
    -webkit-box-shadow:
      inset 0 0 8px  rgba(0,0,0,0.1),
            0 0 16px rgba(0,0,0,0.1);
    -moz-box-shadow:
      inset 0 0 8px  rgba(0,0,0,0.1),
            0 0 16px rgba(0,0,0,0.1);
    box-shadow:
      inset 0 0 8px  rgba(0,0,0,0.1),
            0 0 16px rgba(0,0,0,0.1);
    padding: 15px;
    background: rgba(255,255,255,0.5);
    margin-bottom: 0.8em !important;
    border-radius: 50px;
    width: 67%;
}
.submitArtwork{
  border: 3px solid white;
  border-radius: 50px;
}
.submitArtwork:hover{
  background-color: rgb(175, 146, 108);
  color: white;
}
.homeCardBuy{
  border-radius: 30px !important;
  overflow: hidden !important;
}
.listingInfoBuy{
  border-radius: 30px !important;
}
.cardBodyBuy{
  color: rgb(63, 57, 53);
}
</style>
