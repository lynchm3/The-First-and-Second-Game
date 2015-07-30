attribute vec2 Position;
attribute vec2 TexCoord;
attribute vec4 Color;

uniform mat4 u_projView;

varying vec2 vTexCoord;
varying vec4 vColor;

void main() {
  vColor = Color;
  vTexCoord = TexCoord;
  gl_Position = u_projView * vec4(Position, 0.0, 1.0);
}