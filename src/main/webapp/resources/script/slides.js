var sslide = {
  /* (A) INITIALIZE SLIDESHOW */
  instances : [],
  init : function (opt) {
    // (A1) REGISTER SLIDESHOW INSTANCE
    let id = sslide.instances.length;
    sslide.instances.push(opt);

    // (A2) PRELOAD IMAGES
    let all = opt.images.length,
        loaded = 0;
    for (let i of opt.images) {
      let img = document.createElement("img");
      img.src = i.src;
      img.onload = function(){
        loaded++;
        if (loaded == all) { sslide.attach(id); }
      };
    }
  },

  /* (B) INITIALIZE - ATTACH CONTROLS */
  attach : function (id) {
    // (B1) CONTAINER ITSELF
    let inst = sslide.instances[id],
        sSlide = document.getElementById(inst.target);
    sSlide.classList.add("sSlide");

    // (B2) TOP HALF - IMAGES + CONTROLS
    let sTop = document.createElement("div"),
        sImg = document.createElement("div"),
        sLeft = document.createElement("div"),
        sRight = document.createElement("div");
    sTop.classList.add("sTop");
    sImg.classList.add("sImg");
    sLeft.classList.add("sLeft");
    sRight.classList.add("sRight");
    sLeft.innerHTML = "&lt;";
    sRight.innerHTML = "&gt;";
    sLeft.addEventListener("click", function(){
      sslide.nav(id, 0);
    });
    sRight.addEventListener("click", function(){
      sslide.nav(id, 1);
    });
    sSlide.appendChild(sTop);
    sTop.appendChild(sImg);
    sTop.appendChild(sLeft);
    sTop.appendChild(sRight);
    
    // (B3) BOTTOM HALF - CAPTION
    let sBottom = document.createElement("div");
    sBottom.classList.add("sBottom");
    sSlide.appendChild(sBottom);
    
    // (B4) READY!
    inst.current = -1;
    inst.sImg = sImg;
    inst.sBottom = sBottom;
    sslide.nav(id, 1);
    
    if (inst.auto) {
      inst.timer = setInterval(function(){
        sslide.nav(id, 1);
      }, inst.auto); 
    }
  },

  /* (C) NAVIGATION */
  nav : function (id, direction) {
    // (C1) CALCULATE NEXT SLIDE
    let inst = sslide.instances[id],
        slides = inst.images;
    if (direction) { inst.current++; }
    else { inst.current--; }
    if (inst.current < 0) { inst.current = slides.length - 1; }
    if (inst.current >= slides.length) { inst.current = 0; }

    // (C2) DRAW SLIDE
    let img = document.createElement("img");
    img.src = slides[inst.current].src;
    inst.sImg.innerHTML = "";
    inst.sImg.appendChild(img);
    inst.sBottom.innerHTML = slides[inst.current].cap;
  }
};