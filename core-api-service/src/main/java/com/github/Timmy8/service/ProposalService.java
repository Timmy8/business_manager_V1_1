package com.github.Timmy8.service;

import com.github.Timmy8.entity.Client;
import com.github.Timmy8.entity.Proposal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProposalService {
    List<Proposal> findAllProposals();
    Optional<Proposal> findProposal(Integer proposalId);
    Proposal createProposal(String name, String description, BigDecimal price);
    void updateProposal(Integer proposalId, String name, String description, BigDecimal price);
    void deleteProposal(Integer proposalId);
}
