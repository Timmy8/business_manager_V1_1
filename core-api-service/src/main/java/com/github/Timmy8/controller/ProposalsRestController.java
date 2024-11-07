package com.github.Timmy8.controller;

import com.github.Timmy8.controller.payload.NewProposalPayload;
import com.github.Timmy8.entity.Proposal;
import com.github.Timmy8.service.NotificationProducer;
import com.github.Timmy8.service.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("manager-api/proposals")
public class ProposalsRestController {
    private final Logger logger = LogManager.getLogger(ProposalsRestController.class.getName());
    private final ProposalService service;
    private final NotificationProducer producer;

    @GetMapping
    public List<Proposal> findAllProposals(){
        return service.findAllProposals();
    }

    @PostMapping
    public ResponseEntity<?> createProposal(@RequestBody @Valid NewProposalPayload payload,
                                         BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors())
            throw new BindException(bindingResult);
        else {
            Proposal proposal = service.createProposal(payload.name(), payload.description(), payload.price());

            logger.info("\n--- New Proposal added ---\n{}\n---\n", proposal);
            producer.sendNotification("New Proposal added: " + proposal);
            return ResponseEntity
                    .created(URI.create("manager-api/proposals/" + proposal.getId()))
                    .body(proposal);
        }
    }
}
