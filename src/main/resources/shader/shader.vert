#version 330 core

in vec3 position;
in vec2 textureCoords;
in vec3 normals;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

uniform vec3 lightPos[2];

out vec2 passTextureCoords;
out vec3 unitNormal;
out vec3 unitVectorToCamera;
out vec3 toLightVector[2];
out float visibility;


const float density = 0.00245;
const float gradient = 2.85;

void main(){

    vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
    vec4 relativePositionToCamera =  viewMatrix * worldPosition;
    gl_Position = projectionMatrix * relativePositionToCamera;
    passTextureCoords = textureCoords;

    vec3 surfaceNormals = (transformationMatrix * vec4(normals, 0.0)).xyz;

    vec3 toCameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;

    for (int i = 0; i < 2; i++){
        toLightVector[i] = lightPos[i] - worldPosition.xyz;
    }
    unitNormal = normalize(surfaceNormals);
    unitVectorToCamera = normalize(toCameraVector);

    float distance = length(relativePositionToCamera.xyz);
    visibility = exp(-pow((distance * density), gradient));
    visibility = clamp(visibility, 0.0, 1.0);
}