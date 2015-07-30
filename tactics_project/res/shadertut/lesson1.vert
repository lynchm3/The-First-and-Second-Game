//incoming Position attribute from our SpriteBatch
attribute vec2 Position;

//the transformation matrix of our SpriteBatch
uniform mat4 u_projView;
 
void main() {
	//transform our 2D screen space position into 3D world space
	gl_Position = u_projView * vec4(Position, 0.0, 1.0);
}


//attribute vec4 a_position;
//attribute vec4 a_color;
//attribute vec2 a_texCoord0;
//uniform mat4 u_projTrans;

//varying vec2 vTexCoord0;
//varying vec4 vColor;

//void main() {
//  vColor = a_color;
//  vTexCoord0 = a_texCoord0;
//  gl_Position = u_projTrans * a_position;
//}