#version 400 core

in vec2 in_position;

out vec4 clipSpace;
out vec2 textureCoords;
out vec3 toCameraVector;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec3 cameraPosition;

const float tiling = 3.0;

void main(void) {

    vec4 worldPosition = modelMatrix * vec4(in_position.x, 0.0, in_position.y, 1.0);
    clipSpace = projectionMatrix * viewMatrix * worldPosition;
    gl_Position = clipSpace;

    textureCoords = vec2(in_position.x/2.0 + 0.5, in_position.y/2.0 + 0.5)*tiling;

    toCameraVector = cameraPosition - worldPosition.xyz;

}