package com.gotgam.bansi.service;

// @Transactional
// @Service
// public class PieceImageService {
//     private final PieceImageRepository pieceImageRepository;
//     private final PieceRepository pieceRepository;

//     public PieceImageService(PieceImageRepository pieceImageRepository, PieceRepository pieceRepository){
//         this.pieceImageRepository = pieceImageRepository;
//         this.pieceRepository = pieceRepository;
//     }
//     public String uploadImage(MultipartFile multipartFile, Long pieceId){
//         String filename = String.valueOf(pieceId) + "/" + createFilename(multipartFile.getOriginalFilename());

//         ObjectMetadata objectMetadata = new ObjectMetadata();
//         objectMetadata.setContentLength(multipartFile.getSize());
//         objectMetadata.setContentType(multipartFile.getContentType());
//         try (InputStream inputStream = multipartFile.getInputStream()){
//             uploadService.uploadFile(inputStream, objectMetadata, filename);
//         } catch (IOException e) {
//             throw new IllegalArgumentException(String.format("파일 변환 중 에러 발생  %s", multipartFile.getOriginalFilename()));
//         }
//         Piece piece = pieceRepository.findById(pieceId).orElseThrow(() -> new NotFoundException("wrong pieceId"));
//         // imageRepository.save(new PieceImage(piece, filename));
//         return uploadService.getFileUrl(filename);
//     }

//     private String getFileExtension(String filename){
//         try {
//             return filename.substring(filename.lastIndexOf("."));
//         } catch (StringIndexOutOfBoundsException e) {
//             throw new IllegalArgumentException(String.format("잘못된 형식의 파일 %s 입니다.", filename));
//         }
//     }

//     private String createFilename(String originalFilename){
//         return UUID.randomUUID().toString().concat(getFileExtension(originalFilename));
//     }

//     public String downloadImage(String filename){
//         return uploadService.getFileUrl(filename);
//     }

//     // public List<String> getImageUrl(Long pieceId){
//     //     Piece piece = pieceRepository.findById(pieceId).orElseThrow(() -> new NotFoundException("wrong pieceId"));
//     //     List<PieceImage> pieceImages = pieceImageRepository.findByPiece(piece);
//     //     List<String> images = new ArrayList<>();
//     //     for(PieceImage pieceImage : pieceImages){
//     //         images.add("https://jinho.s3.ap-northeast-2.amazonaws.com/" + pieceImage.getImageUrl());
//     //     }
//     //     return images;
//     // }
// }
