
  <template>
  <div id="app">
    <nav class="navbar navbar-expand-lg navbar-light">
  <a class="navbar-brand" href="#"><img id="navlogo" src="./assets/navlogo0.png" alt="nav"></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/about">About</a>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0" id="searchForm">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn my-2 my-sm-0" type="submit"><img id="searchIcon" src="./assets/loupe.png" alt="Search"></button>
    </form>
    <ul class="navbar-nav ml-auto" id="loginUl">
        <li class="nav-item loginDiv">
        <a class="nav-link" href="/about">Login</a>
      </li>
      <li class="nav-item loginDiv">
        <a class="nav-link" href="/about">Sign Up</a>
      </li>
    </ul>
  </div>
</nav>
    <router-view />
  </div>
</template>
  <style>
  /*
  Icons made by <a href="https://www.flaticon.com/authors/kiranshastry" title="Kiranshastry">Kiranshastry</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
*/
#hooperContainer{
  width:100%;
  height:100%;
}
.navbar{
background-color:#fff !important;
  position:fixed !important; /* fixing the position takes it out of html flow - knows
                   nothing about where to locate itself except by browser
                   coordinates */
  left:0 !important;           /* top left corner should start at leftmost spot */
  top:0 !important;            /* top left corner should start at topmost spot */
  width:100vw !important;      /* take up the full browser width */
  z-index:200 !important;  /* high z index so other content scrolls underneath */
}
.mainArtContainer{
  height: 532px;
}
.mainArt{
  background-size: 100% auto;
}
#mainArtSection{
  background:#444;
  position:relative;
  margin: auto;
  height:385px;
  width:100%;
  color:#fff;
}
#tagSection{
  background:rgb(202, 193, 176);
  position:relative;
  margin: auto;
  height:60px;
  width:100%;
  color:#fff;
}
.sectionImage{
  width: 100%;
  height:100%;
  background: transparent none no-repeat top top;
  background-size: cover;
}
.sectionContent{
  position: absolute;
  left: 50%;
  transform: translate(-50%, -50%);
}
#sectionContentTitle{
    font-size: 170px;
    top: 50%;
}
.sectionContentListing{
    color:rgb(117, 106, 91);
    font-size:50px;
    top: 30%;
    font-weight:400;
}
#tagContainer{
  width: 98%;
  overflow-x: scroll;
  white-space: nowrap;
  margin:auto
}
.cardImg{
  opacity: 0.4;
  filter: alpha(opacity=40); /* msie */
}
.cardImg:hover{
  opacity: 1;
}
#fixedbutton {
    position: fixed;
    bottom: 5%;
    right: 5%;
    z-index: 9998;
    background-color: rgb(202, 121, 110);
    font-size:200%;
    font-weight: 600;
    border-radius: 50%;
    height: 70px;
    width: 70px;
    border-width: 0px;
}
#mainHomeArt1{
  background-image: url(https://www.slantmagazine.com/wp-content/uploads/2010/05/picassoandbraquegotothemovies.jpg);
}
#mainHomeArt2{
  background-image: url(https://media.architecturaldigest.com/photos/5ab021fad91fc303af543aa3/16:9/w_2560%2Cc_limit/56797527.jpg);
}
#mainHomeArt3{
  background-image: url(https://thisiscolossal.com/wp-content/uploads/2011/01/odani-5.jpg);
}
#mainHomeArt4{
  background-image: url(https://static01.nyt.com/images/2011/04/05/opinion/editorial_tooker/editorial_tooker-jumbo.jpg);
}
#navLogo{
  height: auto;
    width: auto;
    max-height: 72px;
    max-width: 250px;
}
.tagBtn{
  margin-top:10px;
  margin-bottom:10px;
  margin-left: 5px;
  margin-right: 5px;
  padding:0px;
  height:40px;
  width:150px;
  border-color:rgb(231, 215, 191) !important;
  background-color:#fff !important;
  color: rgb(146, 135, 113) !important;
}
.tagBtn:hover{
  background-color:rgb(153, 153, 131) !important;
  color: rgb(226, 226, 220) !important;
  border-color:rgb(163, 155, 142) !important;
}
.listingContainer{
  padding:75px;
  background-color:#fff;
}
.homeCard{
  width:350px !important;
  margin:30px !important;
}
.cardArtist{
float:left;
}
.cardPrice{
float:right;
}
.cardDesc{
float:left;
padding-top:10%;
padding-bottom:5%;
}
.cardHeader{
display:inline;
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
</style>
