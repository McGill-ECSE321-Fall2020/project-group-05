
  <template>
  <div id="app">
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div>
    <router-view />
  </div>
</template>
  <style>
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
  background:rgb(146, 142, 131);
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
    color:rgb(78, 71, 61);
    font-size:50px;
    top: 30%;
    font-weight:400;
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
#mainHomeArt{
  background-image: url(https://www.slantmagazine.com/wp-content/uploads/2010/05/picassoandbraquegotothemovies.jpg);
}
.tagBtn{
  margin-top:10px;
  margin-bottom:10px;
  margin-left: 5px;
  margin-right: 5px;
  padding:0px;
  height:40px;
  width:150px;
  border-color:rgb(189, 175, 154) !important;
  background-color:rgb(226, 226, 220) !important;
  color: rgb(153, 153, 131) !important;
}
.tagBtn:hover{
  background-color:rgb(153, 153, 131) !important;
  color: rgb(226, 226, 220) !important;
  border-color:rgb(163, 155, 142) !important;
}
.listingContainer{
  padding:75px;
  background-color:rgb(226, 226, 220);
}
.card{
  width:350px !important;
  margin:30px !important;
}
h4{
float:left;
}
h5{
float:right;
}
p{
float:right;
padding-bottom:10%;
}
.cardHeader{
display:inline;
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
