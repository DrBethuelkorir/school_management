package com.trialweb.school_management.Service.Parents;

import com.trialweb.school_management.Dtos.ParentDto;
import com.trialweb.school_management.Exeption.CustomExeption;
import com.trialweb.school_management.Models.Parent;
import com.trialweb.school_management.Repositories.ParentRepository;
import com.trialweb.school_management.Responses.ParentsResponse;
import com.trialweb.school_management.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentsService implements IParentsService {

    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ParentsResponse getAllParents() {
        ParentsResponse parentsResponse = new ParentsResponse();

        try {
            List<Parent> parents = parentRepository.findAll();
            List<ParentDto> parentDto = parents.stream()
                    .map(Utils::mapParentEntityToParentDto).toList();
            parentsResponse.setStatusCode(200);
            parentsResponse.setMessage("success");
            parentsResponse.setParentDtoList(parentDto);
        } catch (CustomExeption e) {
            parentsResponse.setStatusCode(404);
            parentsResponse.setMessage(e.getMessage());
        }catch (Exception e) {
            parentsResponse.setStatusCode(500);
            parentsResponse.setMessage(e.getMessage());
        }

        return parentsResponse;
    }

    @Override
    public ParentsResponse getParentById(Long id) {
        ParentsResponse parentsResponse = new ParentsResponse();

        try {
            Parent parentEntity = parentRepository.findById(id)
                    .orElseThrow(() -> new CustomExeption("Parent not found"));
            ParentDto parentDto = Utils.mapParentEntityToParentDto(parentEntity);
            parentsResponse.setStatusCode(200);
            parentsResponse.setMessage("success");
            parentsResponse.setParentDto(parentDto);
        } catch (CustomExeption e) {
            parentsResponse.setStatusCode(404);
            parentsResponse.setMessage(e.getMessage());
        }catch (Exception e) {
            parentsResponse.setStatusCode(500);
            parentsResponse.setMessage(e.getMessage());
        }
        return parentsResponse;
    }

    @Override
    public ParentsResponse registerParent(ParentDto parentDto) {
        ParentsResponse parentsResponse = new ParentsResponse();

        try {
            if (parentRepository.existsByEmail(parentDto.getEmail())) {
                throw new CustomExeption("Email already exists: " + parentDto.getEmail());
            }

            Parent parent = new Parent();
            parent.setEmail(parentDto.getEmail());
            parent.setPassword(passwordEncoder.encode(parentDto.getPassword()));
            parent.setFirstName(parentDto.getFirstName());
            parent.setLastName(parentDto.getLastName());
            parent.setPhoneNumber(parentDto.getPhoneNumber());
            parent.setAddress(parentDto.getAddress());
            parent.setRelationshipWithStudent(parentDto.getRelationshipWithStudent());

            Parent savedParent = parentRepository.save(parent);
            ParentDto savedParentDto = Utils.mapParentEntityToParentDto(savedParent);

            parentsResponse.setStatusCode(201);
            parentsResponse.setMessage("Parent registered successfully");
            parentsResponse.setParentDto(savedParentDto);
        } catch (CustomExeption e) {
            parentsResponse.setStatusCode(409);
            parentsResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            parentsResponse.setStatusCode(500);
            parentsResponse.setMessage(e.getMessage());
        }

        return parentsResponse;
    }

    @Override
    public ParentsResponse updateParent(ParentDto parentDto) {
        ParentsResponse parentsResponse = new ParentsResponse();

        try {
            Parent existingParent = parentRepository.findById(parentDto.getId())
                    .orElseThrow(() -> new CustomExeption("Parent not found with id: " + parentDto.getId()));

            existingParent.setFirstName(parentDto.getFirstName());
            existingParent.setLastName(parentDto.getLastName());
            existingParent.setPhoneNumber(parentDto.getPhoneNumber());
            existingParent.setAddress(parentDto.getAddress());
            existingParent.setRelationshipWithStudent(parentDto.getRelationshipWithStudent());

            if (parentDto.getPassword() != null && !parentDto.getPassword().isEmpty()) {
                existingParent.setPassword(passwordEncoder.encode(parentDto.getPassword()));
            }

            Parent updatedParent = parentRepository.save(existingParent);
            ParentDto updatedParentDto = Utils.mapParentEntityToParentDto(updatedParent);

            parentsResponse.setStatusCode(200);
            parentsResponse.setMessage("Parent updated successfully");
            parentsResponse.setParentDto(updatedParentDto);
        } catch (CustomExeption e) {
            parentsResponse.setStatusCode(404);
            parentsResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            parentsResponse.setStatusCode(500);
            parentsResponse.setMessage(e.getMessage());
        }

        return parentsResponse;
    }

    @Override
    public ParentsResponse deleteParent(Long id) {
        ParentsResponse parentsResponse = new ParentsResponse();

        try {
            Parent parent = parentRepository.findById(id)
                    .orElseThrow(() -> new CustomExeption("Parent not found with id: " + id));

            parentRepository.delete(parent);

            parentsResponse.setStatusCode(200);
            parentsResponse.setMessage("Parent deleted successfully");
        } catch (CustomExeption e) {
            parentsResponse.setStatusCode(404);
            parentsResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            parentsResponse.setStatusCode(500);
            parentsResponse.setMessage(e.getMessage());
        }

        return parentsResponse;
    }
}
