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
export default {
  data: function () {
    return {
      title: 'Art Piece Title',
      artistName: 'Insert artist name',
      description: 'Insert art piece description',
      price: 'Insert price'
    }
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
