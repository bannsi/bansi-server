package com.gotgam.bansi.service;

// @RequiredArgsConstructor
// @Component
// public class AWSS3UploadService implements UploadService{
//     private final AmazonS3 amazonS3;
//     private final S3Component component;

//     @Override
//     public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String filename) {
//         amazonS3.putObject(new PutObjectRequest(component.getBucket(), filename, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
//     }

//     @Override
//     public String getFileUrl(String filename){
//         return amazonS3.getUrl(component.getBucket(), filename).toString();
//     }
// }
