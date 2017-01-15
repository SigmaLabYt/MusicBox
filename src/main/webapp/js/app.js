var app = angular.module("MusicBox", ["ngRoute"]);
app.config(function ($routeProvider, $locationProvider) {
  $routeProvider
  .when("/", {
      templateUrl: "views/index.html"
  })
  .when("/search/:keyword", {
      templateUrl: "views/search.html",
      controller: "SearchResult"
  });

})

app.controller('DiscoverCtrl', function($scope, $http) {
    $http.get("http://localhost:8081/musicbox/webapi/LastFm?country=spain&limit=12")
    .then(function(response) {
        var obj = angular.fromJson(response.data);
  		  var trackArray = obj.tracks.track;
        $scope.tracks = trackArray;
    });
});

app.controller('SearchResult', function($scope, $http, $location, $routeParams) {
    var keyword = $routeParams.keyword;
    if($('.search-block input').val()==""){
      $location.path("/");
    }else{
      if(keyword == null){
        keyword=$('.search-block input').val();
      }
      ytUrl = "http://localhost:8081/musicbox/webapi/YoutubeSearch?keyword="+keyword+"&limit=12";
      $http.get(ytUrl)
      .then(function(response) {
          var obj = angular.fromJson(response.data);
          $scope.searchResult = obj;
      });
    }
    $scope.download=function(videoId){
      youtubeMp3Api = "http://localhost:8081/musicbox/webapi/GetMp3?videoId="+videoId;
      $http.get(youtubeMp3Api)
      .then(function(response) {
          console.log(response.data);
          
      });
    }
});

app.controller('SearchCtrl', function($scope, $location) {
  $scope.change=function($event){
    var code = $event.which;
    if(code==27){
      $('.search-block input').blur();
    }
    if (code==13){
      var input = $('.search-block input').val();
      if(input.length>0){
        $location.path("/search/"+input);
      }else{
        $location.path("/");
      }
      $('.search-block input').blur();
    }
  }
});
