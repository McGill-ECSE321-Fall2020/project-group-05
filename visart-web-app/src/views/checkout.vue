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
          <label class="titleAdjust">Address</label>
          <input type="text" id="adr" name="address" placeholder="Not required for pick up">
          <label class="leftAlign"> <input type="checkbox" id="check1"/> Ship to address
          </label>
          <label class="leftAlign"> <input type="checkbox" id="check2"/> Pick up at gallery
          </label>
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

export default {
  data: function () {
    return {
      pressed: ''
    }
  },
  methods: {
    check: function () {
      if (document.getElementById('check1').checked || document.getElementById('check2').checked) {
        return true
      } else {
        alert('Please check delivery or pick up options')
        event.preventDefault()
      }
    }
  }
}
</script>
