<template>
<div class="container container-space">
  <div class="row">
    <div class="col-xl">
      <div class="card" style="width: 35rem;">
        <img class="card-img-top" src="../assets/logo.png" alt="Card image cap">
        <div class="card-body">
          <h5 class="card-title">{{title}}</h5>
        </div>
      </div>
    </div>
    <div class="col-sm">
      <ul class="list-group">
        <li class="list-group-item list-group-item-secondary">Artist:</li>
        <li class="list-group-item">{{artistName}}</li>
        <li class="list-group-item list-group-item-secondary">Description:</li>
        <li class="list-group-item">{{description}}</li>
        <li class="list-group-item list-group-item-secondary">Price:</li>
        <li class="list-group-item">{{price}}</li>
      </ul>
      <button type="button" class="btn btn-secondary btn-lg btn-block btn-space">Buy Now</button>
      <button type="button" class="btn btn-secondary btn-lg btn-block btn-space">Add to favorite</button>
    </div>
  </div>
</div>
</template>

<script>
// backend.js has on it: backend.get(path,params), backend.post(path,data), backend.parse(json) => post data
var backend = require('@/tools/backend')
export default {
  data: function () {
    return {
      title: '',
      artistName: '',
      description: '',
      price: ''
    }
  },
  created: function () {
    backend
      .get('/artlisting/get_all')
      .then(function (response) {
        this.title = JSON.parse(response.data).title
        this.artistName = JSON.parse(response.data).artistName
        this.description = JSON.parse(response.data).description
        this.price = JSON.parse(response.data).price
      })
      .catch(e => {
        console.log(e)
      })
  }
}
</script>

<style>
.btn-space {
  margin-top: 50px;
}

.container-space {
  margin-top: 35px;
}
</style>
