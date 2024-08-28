package com.github.Timmy8.controller;

import com.github.Timmy8.controller.payload.UpdateProposalPayload;
import com.github.Timmy8.entity.Proposal;
import com.github.Timmy8.service.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("manager-api/proposals/{proposalId:\\d+}")
public class ProposalRestController {
    private final Logger logger = LogManager.getLogger(ProposalsRestController.class.getName());
    private final ProposalService service;

    @GetMapping
    public Proposal getClient(@PathVariable("proposalId") Integer proposalId){
        return service.findProposal(proposalId)
                .orElseThrow(() -> new NoSuchElementException("api.proposal.create.errors.proposal_not_found"));
    }

    @PatchMapping
    public ResponseEntity<?> updateProduct(@PathVariable("proposalId") Integer proposalId,
                                           @RequestBody @Valid UpdateProposalPayload payload,
                                           BindingResult bindingResult) throws BindException{

        if (bindingResult.hasErrors())
            throw new BindException(bindingResult);
        else {
            service.updateProposal(proposalId, payload.name(), payload.description(), payload.price());

            logger.info("\n--- Update Proposal ---\n#" + payload + "\n---\n");

            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProposal(@PathVariable("proposalId") Integer proposalId){
        service.deleteProposal(proposalId);

        logger.info("\n--- Delete Proposal ---\n#" + proposalId + "\n---\n");

        return ResponseEntity.noContent().build();
    }
}
