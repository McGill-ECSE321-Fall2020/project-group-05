<template>
<div id="demoFont" class="text-space center">
<div id="demoFont" style="font-size: 80px; color: black; font-family: 'Arial Black', Gadget, sans-serif; font-weight: 400;">Thank you!</div>
<div class="btn-space" style="color: rgb(146, 135, 113); font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: medium; font-weight: 400; letter-spacing: normal; word-spacing: 0px;">Your order has been processed succesfully.</div>
<div style="color: rgb(146, 135, 113); font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: medium; font-weight: 400; letter-spacing: normal; word-spacing: 0px;">&nbsp;</div>
<div style="color: rgb(146, 135, 113); font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif; font-size: medium; font-weight: 400; letter-spacing: normal; word-spacing: 0px;">Please do not hesitate to contact us for further inquiries.</div>
<b-button v-on:click="goBackHome()" style="background-color: rgb(146, 135, 113);" class="btn-lg btn-space btnPurchase">View more art pieces</b-button>
</div>
</template>
<style>
font-family: Impact, Charcoal, sans-serif;
font-size: 25px;
letter-spacing: 2px;
word-spacing: 2px;
color: #D8AB60;
font-weight: 700;
text-decoration: none;
font-style: normal;
font-variant: normal;
text-transform: none;

.btnPurchase {
  background-color: rgb(146, 135, 113) !important;
  color: #fff !important;
}

.btn-space {
  margin-top: 50px;
}

.center {
  text-align: center;
}

.text-space {
  margin-top:300px;
}
</style>
<script>
export default {
  methods: {
    goBackHome: function () {
      this.$router.push({ path: '/' })
    }
  }
}
</script>
