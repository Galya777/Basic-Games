?-
  G_X:=100,
  G_Y:=100,
  G_Xs:=10,
  G_Ys:=10,
  G_hX:=100,
  G_hY:=300,
  G_hXs:=0,
  G_hYs:=0,
  G_Points:=0,
  G_Player1 := 0,
  G_Player2 := 0,
  G_h1X := 900,
  G_h1Y := 300,
  G_h1Xs:=0,
  G_h1Ys:=0,
  window(size(1000, 650), title("Air Hochey"), pos(10,50), paint_indirectly).

calc:-
  G_X:=G_X+G_Xs,
  G_Y:=G_Y+G_Ys.

calc_H:-
  G_hX:=G_hX+G_hXs,
  G_hY:=G_hY+G_hYs,
  G_h1X:=G_h1X+G_h1Xs,
  G_h1Y:=G_h1Y+G_h1Ys.

win_func(init) :-
  _ := set_timer(_, 0.04, time_func).

win_func(key_down(37, Rep)):- %left
    G_h1Xs:=G_h1Xs-1.
win_func(key_down(39, Rep)):- %right
    G_h1Xs:=G_h1Xs+1.  
win_func(key_down(38, Rep)):- % up
    G_h1Ys:=G_h1Ys-1.
win_func(key_down(40, Rep)):- % down
    G_h1Ys:=G_h1Ys+1.  
win_func(mouse_move(X, Y)):-
    G_hX:=X,
    G_hY:=Y.

win_func(paint):-
  pen(0, 0),
  text_out("Balls "+G_Points, pos(20, 20)),
  text_out("Player1="+G_Player1, pos(20, 40)),
  text_out("Player2="+G_Player2, pos(20, 60)),
  brush(rgb(0,0, 0)),
  ellipse(G_X-5, G_Y-5, G_X+5, G_Y+5),
  brush(rgb(0,0, 255)),
  ellipse(G_hX-25, G_hY-25, G_hX+25, G_hY+25),
  brush(rgb(0,50, 255)),
  ellipse(G_h1X-25, G_h1Y-25, G_h1X+25, G_h1Y+25),
  pen(3, rgb(255, 0, 0)),
  line(970,250, 970, 10, 10, 10, 10, 250),
  line(10, 350, 10, 600,970 ,600, 970, 350).

time_func(end) :-
  calc,
  calc_H,
  (G_Y+5>600 ; G_Y-5<10 ->
    G_Ys:= -1*G_Ys, calc
  ), 
  (G_X+5>970 ; G_X-5<10 ->
    (G_Y>250 , G_Y<350 ->
        G_X:=480,
        G_Y:=295,
       G_Points:=G_Points+1
        else 
        G_Xs:= -1*G_Xs, calc
    )
  ),
(G_X+5>970 ->
(G_Y>250 , G_Y<350 ->
        G_X:=480,
        G_Y:=295,
      G_Player1 := G_Player1 + 1
    else 
        G_Xs:= -1*G_Xs, calc
)
),
(G_X-5<10 ->
 (G_Y>250 , G_Y<350 ->
      G_X:=480,
        G_Y:=295,
      G_Player2 := G_Player2 + 1
     else 
        G_Xs:= -1*G_Xs, calc
   )
 ),
  (G_hY+25>600 ; G_hY-25<10 ->
    G_hYs:= -1*G_hYs, calc
  ), 
  (G_hX+25>970 ; G_hX-25<10 ->
    G_hXs:= -1*G_hXs, calc
  ),
  ((G_hX-G_X)**2+(G_hY-G_Y)**2<(25+5)**2->
    G_Xs:=G_X-G_hX,
    G_Ys:=G_Y-G_hY
  ),
 (G_h1Y+25>600 ; G_h1Y-25<10 ->
    G_h1Ys:= -1*G_h1Ys, calc
  ), 
  (G_h1X+25>970 ; G_h1X-25<10 ->
    G_h1Xs:= -1*G_h1Xs, calc
  ),
  ((G_h1X-G_X)**2+(G_h1Y-G_Y)**2<(25+5)**2->
    G_Xs:=G_X-G_h1X,
    G_Ys:=G_Y-G_h1Y
  ),

  update_window(_).
 