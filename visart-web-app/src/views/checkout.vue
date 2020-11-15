<template>
<div class="row">
  <div class="col-75">
    <div class="container">
      <div class="row">
        <div class="col-50">
          <h3>Almost yours!</h3>
          <h3> </h3>
          <h3> </h3>
          <form id="sectionForm" action="/purchasepage">
          <label class="titleAdjust">Email</label>
          <input type="text" id="adr" name="address" placeholder="Enter email" required>
          <label class="titleAdjust">Phone number</label>
          <input type="text" id="adr" name="address" placeholder="Enter phone number">
          <input class="leftAlign" type="radio" id="add" name="method" v-on:click="show()"/>
          <label for="add">Ship to address</label><br>
          <input type="text" name="address" placeholder="Please enter your address here" id="addressButton" style="display: none">
          <input class="leftAlign" type="radio" id="pick" v-on:click="hide()" name="method"/>
          <label for="pick">Pick up at gallery</label><br>
          <input type="submit" value="Place Order" class="tagBtn" v-on:click="check()">
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
</template>
<style>
.row {
  display: -ms-flexbox; /* IE10 */
  display: flex;
  -ms-flex-wrap: wrap; /* IE10 */
  flex-wrap: wrap;
  margin: 0 -16px;
}

.col-25 {
  -ms-flex: 25%; /* IE10 */
  flex: 25%;
}

.col-50 {
  -ms-flex: 50%; /* IE10 */
  flex: 50%;
}

.col-75 {
  -ms-flex: 75%; /* IE10 */
  flex: 75%;
}

.col-25,
.col-50,
.col-75 {
  padding: 0 16px;
}

.container {
  background-color: #f2f2f2;
  padding: 5px 20px 15px 20px;
  border: 1px solid lightgrey;
  border-radius: 3px;
}

input[type=text] {
  width: 100%;
  margin-bottom: 20px;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 3px;
}

label {
  margin-bottom: 10px;
  display: block;
}

.icon-container {
  margin-bottom: 20px;
  padding: 7px 0;
  font-size: 24px;
}

span.price {
  float: right;
  color: grey;
}

.titleAdjust {
    text-align:left;
    line-height:150%;
    font-size:1em;
}

.leftAlign {
  text-align:left;
}

/* Responsive layout - when the screen is less than 800px wide, make the two columns stack on top of each other instead of next to each other (and change the direction - make the "cart" column go on top) */
@media (max-width: 800px) {
  .row {
    flex-direction: column-reverse;
  }
  .col-25 {
    margin-bottom: 20px;
  }
}</style>
<script>
// backend.js has on it: backend.get(path,params), backend.post(path,data), backend.parse(json) => post data
var backend = require('@/tools/backend')
export default {
  data: function () {
    return {
      pieceId: '',
      ArtPieceLocation: ''
    }
  },
  methods: {
    placeOrder: function () {
      backend
        .post('/artorder/create', backend.parse({
          aIsDelivered: 'false',
          pieceLocation: this.ArtPieceLocation,
          aTargetAddress: 'a',
          aDeliveryTracker: 'a',
          artPieceId: this.pieceId
        })
        )
        .then(function (response) {

        })
        .catch(e => {
          console.log(e)
        })
    },
    preSetPieceLocation: function () {
      backend
        .get('/artpiece/get/' + this.pieceId)
        .then(this.setPieceLocation)
        .catch(e => {
          console.log(e)
        })
    },
    setPieceLocation: function (response) {
      this.ArtPieceLocation = (response.data).basicLocation
    },
    check: function () {
      if (document.getElementById('check1').checked || document.getElementById('check2').checked) {
        this.placeOrder()
      } else {
        alert('Please check delivery or pick up options')
        event.preventDefault()
      }
    },
    show: function () {
      document.getElementById('addressButton').style.display = 'block'
    },
    hide: function () {
      document.getElementById('addressButton').style.display = 'none'
    }
  },
  create: function () {
    this.pieceId = this.$route.params.id
    this.preSetPieceLocation()
  }
}
</script>
