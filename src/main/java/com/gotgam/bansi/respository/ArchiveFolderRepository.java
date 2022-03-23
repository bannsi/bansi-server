package com.gotgam.bansi.respository;

import java.util.List;

import com.gotgam.bansi.model.ArchiveFolder;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveFolderRepository extends CrudRepository<ArchiveFolder, Long> {
    List<ArchiveFolder> findAllByUser_Id(String userId);
}
