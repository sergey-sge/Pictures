package com.gmail.sge.serejka.pictures;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class PictureService {
    private final PictureRepository pictureRepository;

    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Transactional
    public void addPicture(Picture picture) {
        pictureRepository.save(picture);
    }

    @Transactional
    public long count() {
        return pictureRepository.count();
    }

    @Transactional
    public void deletePictures(long[] idList) {
        for (long id : idList) {
            pictureRepository.deleteById(id);
        }
    }

    @Transactional
    public void archivatePictures(long[] idList) {
        String path = "src/main/webapp/WEB-INF/static/";
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("archive.zip"))) {
            for (long id : idList) {
                String fileName = pictureRepository.getReferenceById(id).getName() + ".jpeg";
                File pic = new File(path, fileName);
                FileInputStream fileInputStream = new FileInputStream(pic);
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(zipEntry);
                byte[] buffer = new byte[fileInputStream.available()];
                fileInputStream.read(buffer);
                zipOutputStream.write(buffer);
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public List<Picture> findAll(Pageable pageable) {
        return pictureRepository.findAll(pageable).getContent();
    }

    @Transactional
    public void setPic() {
        addPicture(new Picture("koala"));
        addPicture(new Picture("fox"));
        addPicture(new Picture("bird"));

    }
}